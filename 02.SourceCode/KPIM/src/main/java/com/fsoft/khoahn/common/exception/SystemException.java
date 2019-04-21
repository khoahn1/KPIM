package com.fsoft.khoahn.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SystemException() {
        super("");
    }

    public SystemException(String msg) {
        super(msg);
        log.error(msg);
    }

    public SystemException(String msg, Throwable throwable) {
        super(msg, throwable);
        log.error(msg, throwable);
    }
}
