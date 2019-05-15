package com.fsoft.khoahn.common.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fsoft.khoahn.common.enums.StatusCode;
import com.fsoft.khoahn.common.utils.StringUtils;
import com.fsoft.khoahn.dto.MessageDto;
import com.fsoft.khoahn.model.MessageType;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionValidationHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource msgSource;

	private Object processFieldErrors(List<FieldError> errors) {
		List<MessageDto> messages = new ArrayList<>();
		MessageDto message = null;

		if (errors != null && !errors.isEmpty()) {
		    String fieldName = null;
		    String msg = null;
			for (FieldError error : errors) {
				fieldName = parseFieldName(error.getField());
				msg = msgSource.getMessage(error.getDefaultMessage(), new Object[] {fieldName}, Locale.getDefault());
				message = new MessageDto(MessageType.ERROR, msg, fieldName, error.getCode());
				message.setIndex(getIndexError(error.getField()));
				messages.add(message);
			}
		}
		return messages;
	}

	@ExceptionHandler(value = MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
	private ResponseEntity<?> handleMultipartException(MultipartException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		return handleExceptionInternal(ex, processFieldErrors(result.getFieldErrors()), headers, status, request);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		BindingResult result = ex.getBindingResult();
		return handleExceptionInternal(ex, processFieldErrors(result.getFieldErrors()), headers, status, request);
	}

	@ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<Object> handleInternalError(final Exception exception) {
        log.error(exception.getMessage(), exception);
        MessageDto messageDto = new MessageDto();
        messageDto.setCode(StatusCode.INTERNAL_SERVER_ERROR.getValue());
        messageDto.setMessage(msgSource.getMessage("system.failure", null, Locale.getDefault()));
        messageDto.setType(MessageType.ERROR);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

	private int getIndexError(String fieldName) {
	    if (StringUtils.contains(fieldName, "[") && StringUtils.contains(fieldName, "]")) {
	        StringTokenizer tokenizer = new StringTokenizer(fieldName, ".");
	        if (tokenizer.hasMoreTokens()) {
	            String token = tokenizer.nextToken();
	            String substring = token.substring(token.indexOf("[") + 1, token.indexOf("]"));
	            if (StringUtils.isNumberic(substring)) {
	                return Integer.parseInt(substring);
	            }
	        }
	    }
	    return 0;
	}

	private String parseFieldName(String fieldName) {
	    if (StringUtils.contains(fieldName, "[") && StringUtils.contains(fieldName, "]")) {
	        String[] fieldNameSplitted = fieldName.split("[.]");
	        int splittedSize = fieldNameSplitted.length;
	        if (splittedSize > 2) {
	            return fieldNameSplitted[splittedSize - 2];
	        } else if (splittedSize == 2) {
	            return fieldNameSplitted[splittedSize - 1];
	        }
	    }
	    return fieldName;
	}

}
