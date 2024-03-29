package com.kvinod.controller;

import com.kvinod.model.ErrorInfo;
import com.kvinod.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ServiceException.class, NullPointerException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        log.error("CustomExceptionHandler.handleConflict() called", ex);
        return handleExceptionInternal(ex, new ErrorInfo(ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
