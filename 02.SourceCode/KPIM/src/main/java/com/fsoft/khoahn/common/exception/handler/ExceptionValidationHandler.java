package com.fsoft.khoahn.common.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fsoft.khoahn.model.MessageType;
import com.fsoft.khoahn.model.dto.MessageDto;

@ControllerAdvice
public class ExceptionValidationHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource msgSource;

	private Object processFieldErrors(List<FieldError> errors) {
		List<MessageDto> messages = new ArrayList<>();
		MessageDto message = null;
		if (errors != null && !errors.isEmpty()) {
			for (FieldError error : errors) {
				Locale currentLocale = LocaleContextHolder.getLocale();
				String msg = msgSource.getMessage(error.getDefaultMessage(), error.getArguments(), currentLocale);
				message = new MessageDto(MessageType.ERROR, msg, error.getField(), error.getCode());
				messages.add(message);
			}
		}
		return messages;
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

}
