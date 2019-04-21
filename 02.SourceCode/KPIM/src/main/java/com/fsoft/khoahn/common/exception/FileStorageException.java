package com.fsoft.khoahn.common.exception;

public class FileStorageException extends RuntimeException {
	private static final long serialVersionUID = 437952649967687680L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}