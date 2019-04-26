package com.fsoft.khoahn.common.download;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.annotation.CellBindByName;
import com.fsoft.khoahn.common.enums.DateTimeFormat;
import com.fsoft.khoahn.common.utils.DateTimeUtils;
import com.fsoft.khoahn.dto.res.DataExportResDto;

public class ExcelGenerator {

	public static <T> DataExportResDto map(T obj, List<T> cls, String uploadFileName, String pathFileUpload)
			throws Exception {
		DataExportResDto resDto = new DataExportResDto();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			Path fileStorageLocation = null;
			fileStorageLocation = Paths.get(Constants.PATH_FILE_UPLOAD + pathFileUpload).toAbsolutePath().normalize();
			File uploadRootDir = fileStorageLocation.toFile();
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}
			String stringTimestamp = DateTimeUtils.convertTimestampToString(new Timestamp(System.currentTimeMillis()),
					DateTimeFormat.SLASH_DDMMYYYY_HHMMSSFF);
			String fileName = uploadFileName + "_" + stringTimestamp + ".xlsx";

			Path targetLocation = fileStorageLocation.resolve(fileName);

			Field[] fields = obj.getClass().getDeclaredFields();

			// Row for Header
			CreationHelper createHelper = workbook.getCreationHelper();

			XSSFSheet sheet = workbook.createSheet("Data");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			XSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			XSSFRow headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < fields.length; col++) {
				Field field = fields[col];
				CellBindByName annotation = field.getAnnotation(CellBindByName.class);
				XSSFCell cell = headerRow.createCell(col);
				cell.setCellValue(annotation.column());
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
			XSSFCellStyle dataCellStyle = workbook.createCellStyle();
			dataCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("@"));

			int rowIdx = 1;
			for (Object object : cls) {
				XSSFRow dataRow = sheet.createRow(rowIdx++);
				for (int colData = 0; colData < fields.length; colData++) {
					Field field = object.getClass().getDeclaredField(fields[colData].getName());
					field.setAccessible(true);
					Object cellValue = field.get(object);
					XSSFCell cell = dataRow.createCell(colData);
					if (cellValue != null) {
						cell.setCellValue(String.valueOf(cellValue));
					}
					cell.setCellStyle(dataCellStyle);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(targetLocation.toFile());
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			
			resDto.setFileDownloadUri(pathFileUpload + "/" + fileName);
			resDto.setFileName(fileName);
			return resDto;
		}
	}
}