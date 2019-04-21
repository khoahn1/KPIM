package com.fsoft.khoahn.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.download.ExcelToObjectMapper;
import com.fsoft.khoahn.common.support.ValidationSupport;
import com.fsoft.khoahn.model.Error;
import com.fsoft.khoahn.model.UserImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.model.response.dto.UserReadResDto;
import com.fsoft.khoahn.service.UserService;

@Component
public class UserImportValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return FileUploadRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		FileUploadRequest fileUploadRequest = (FileUploadRequest) obj;
		MultipartFile file = fileUploadRequest.getFile();
		if (file == null || file.isEmpty()) {
			return;
		}

		ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());

		try {
			List<UserImportExportContent> userImportDatas = mapper.map(UserImportExportContent.class);
			int rowCount = userImportDatas.size();
			for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
				List<Error> rowErrorCodes = new ArrayList<Error>();
				UserImportExportContent uploadUserContentI = userImportDatas.get(rowIndex);
				String idI = uploadUserContentI.getId();
				String usernameI = uploadUserContentI.getUsername();

				// Data import validation
				validImportData(rowIndex, uploadUserContentI, rowErrorCodes);
				// Check duplicate data import
				checkDuplicateData(userImportDatas, rowCount, rowIndex, idI, usernameI, rowErrorCodes);
				// Check exit data
				checkExitUsername(idI, usernameI, rowErrorCodes);

				if (rowErrorCodes == null || rowErrorCodes.isEmpty()) {
					continue;
				}
				errors.rejectValue("file", "import.row.index", new Object[] { String.valueOf(rowIndex + 1) },
						"import.row.index");
				for (Error error : rowErrorCodes) {
					errors.rejectValue("file", error.getMessage(), new Object[] { error.getReason() },
							error.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param userValidator
	 * @param rowIndex
	 * @param rowMessages
	 * @param userCreateReqDto
	 */
	private void validImportData(int rowIndex, UserImportExportContent userImportExportContent, List<Error> rowErrorCodes) {
		if (ValidationSupport.isBlank(userImportExportContent.getUsername())) {
			rowErrorCodes.add(new Error("username", "import.row.field.required"));
		}
		if (ValidationSupport.isBlank(userImportExportContent.getPassword())) {
			rowErrorCodes.add(new Error("password", "import.row.field.required"));
		}
		if (!ValidationSupport.isBlank(userImportExportContent.getEmail())
				&& !ValidationSupport.isEmailValid(userImportExportContent.getEmail())) {
			rowErrorCodes.add(new Error("email", "import.row.field.is.incorrect.format"));
		}
	}

	/**
	 * @param table
	 * @param rowCount
	 * @param rowIndex
	 * @param rowMessages
	 * @param uploadUserContentI
	 */
	private void checkDuplicateData(List<UserImportExportContent> csvUsers, int rowCount, int rowIndex,
			String idI, String usernameI,
			List<Error> rowErrorCodes) {
		boolean isDuplicateId = false;
		boolean isDuplicateUsername = false;
		for (int rowIndexCheck = 0; rowIndexCheck < rowCount; rowIndexCheck++) {
			UserImportExportContent uploadUserContentJ = csvUsers.get(rowIndexCheck);
			if (rowIndex != rowIndexCheck) {
				String idJ = uploadUserContentJ.getId();
				String usernameJ = uploadUserContentJ.getUsername();
				if (!ValidationSupport.isBlank(idI) && idI.equals(idJ) && !isDuplicateId) {
					rowErrorCodes.add(new Error(idI, "user.import.row.is.duplicate.id"));
					isDuplicateId = true;
				}
				if (idI == null && !ValidationSupport.isBlank(usernameI) && usernameI.equals(usernameJ)
						&& !isDuplicateUsername) {
					rowErrorCodes
							.add(new Error(usernameI, "user.import.row.is.duplicate.username"));
					isDuplicateUsername = true;
				}
			}

		}
	}

	/**
	 * @param status
	 * @param rowIndex
	 * @param userImportReqDto
	 * @param userCreateReqDto
	 * @param messages
	 */
	private void checkExitUsername(String idI, String usernameI, List<Error> rowErrorCodes) {
		if (idI != null && !idI.isEmpty()) {
			if (!userService.isExitUserId(idI)) {
				rowErrorCodes.add(new Error(idI, "user.import.row.is.not.exit.id"));
				if (userService.isExitUsername(usernameI)) {
					rowErrorCodes.add(new Error(usernameI, "user.import.row.exit.username"));
				}
			} else {
				UserReadResDto userReadResDto = userService.findOne(idI);
				if (!userReadResDto.getUsername().equals(usernameI)) {
					rowErrorCodes.add(new Error(usernameI, "user.import.row.exit.username"));
				}
			}
		} else {
			if (userService.isExitUsername(usernameI)) {
				rowErrorCodes.add(new Error(usernameI, "user.import.row.exit.username"));
			}
		}
	}

}
