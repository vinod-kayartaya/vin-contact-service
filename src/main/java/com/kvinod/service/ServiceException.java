package com.kvinod.service;

public class ServiceException extends RuntimeException {


    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
