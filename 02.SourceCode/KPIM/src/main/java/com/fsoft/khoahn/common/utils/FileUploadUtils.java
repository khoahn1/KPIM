package com.fsoft.khoahn.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.enums.DateTimeFormat;

//@Service
public class FileUploadUtils {

	public static String uploadAvatar(MultipartFile file, String uploadFileName, String pathFileUpload)
			throws IOException {
		Path fileStorageLocation = null;
		fileStorageLocation = Paths.get(Constants.PATH_FILE_UPLOAD + pathFileUpload).toAbsolutePath()
				.normalize();
		File uploadRootDir = fileStorageLocation.toFile();
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = getFileExtension(originalFilename);
		String stringTimestamp = DateTimeUtils.convertTimestampToString(new Timestamp(System.currentTimeMillis()),
				DateTimeFormat.SLASH_DDMMYYYY_HHMMSSFF);
		String fileName = uploadFileName + "_" + stringTimestamp + "." + extension;
		Path targetLocation = fileStorageLocation.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String fileUrl = Constants.PATH_PROFILE_PICS + "/" + fileName;
		return fileUrl;
	}

	public static void deleteFileByUrl(String fileUrl) throws IOException {
		if (!StringUtils.isEmpty(fileUrl) && !fileUrl.equals(Constants.PATH_DEFAULT_PROFILE_PICS)) {
			Path fileStorageLocation = Paths.get(Constants.PATH_FILE_UPLOAD).toAbsolutePath().normalize();
			Path filePath = fileStorageLocation.resolve(fileUrl);
			Files.deleteIfExists(filePath);
		}
	}

	private static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex < 0) {
			return null;
		}
		return fileName.substring(dotIndex + 1);
	}
}