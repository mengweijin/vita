package com.github.mengweijin.vita.framework.exception.impl;

/**
 * @author Meng Wei Jin
 * 应用
 **/
@SuppressWarnings({"unused"})
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
