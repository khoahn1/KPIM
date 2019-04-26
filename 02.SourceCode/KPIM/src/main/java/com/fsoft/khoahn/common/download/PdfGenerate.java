package com.fsoft.khoahn.common.download;

import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.enums.DateTimeFormat;
import com.fsoft.khoahn.common.utils.DateTimeUtils;
import com.fsoft.khoahn.dto.res.DataExportResDto;

public class PdfGenerate {

	private static final String UTF_8 = "UTF-8";

	public static <T> DataExportResDto generatePdf(T data, String uploadFileName, String pathFileUpload)
			throws Exception {
		DataExportResDto resDto = new DataExportResDto();

		String stringTimestamp = DateTimeUtils.convertTimestampToString(new Timestamp(System.currentTimeMillis()),
				DateTimeFormat.SLASH_DDMMYYYY_HHMMSSFF);
		String fileName = uploadFileName + "_" + stringTimestamp + ".pdf";

		Path fileStorageLocation = null;
		fileStorageLocation = Paths.get(Constants.PATH_FILE_UPLOAD + pathFileUpload).toAbsolutePath().normalize();
		File uploadRootDir = fileStorageLocation.toFile();
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		Path targetLocation = fileStorageLocation.resolve(fileName);

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/templateExportPdf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(UTF_8);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("data", data);

		String renderedHtmlContent = templateEngine.process("template", context);
        String xHtml = convertToXhtml(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();
		renderer.getFontResolver().addFont("templateExportPdf/Code39.ttf", IDENTITY_H, EMBEDDED);

        String baseUrl = FileSystems
                                .getDefault()
				.getPath("src", "main", "resources", "templateExportPdf")
                                .toUri()
                                .toURL()
                                .toString();
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

		FileOutputStream fileOut = new FileOutputStream(targetLocation.toFile());
		renderer.createPDF(fileOut);
		fileOut.close();

		resDto.setFileDownloadUri(Constants.PATH_EXPORT_DATA_USERS + "/" + fileName);
		resDto.setFileName(fileName);
		return resDto;
    }

	private static String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }
}
