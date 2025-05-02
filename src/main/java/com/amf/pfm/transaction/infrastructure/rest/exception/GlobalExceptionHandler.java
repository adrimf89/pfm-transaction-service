package com.amf.pfm.transaction.infrastructure.rest.exception;

import com.amf.pfm.transaction.domain.exception.AccountException;
import com.amf.pfm.transaction.domain.exception.EntityConflictException;
import com.amf.pfm.transaction.domain.exception.EntityNotFoundException;
import com.amf.pfm.transaction.domain.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String EMPTY_STRING = "";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultHandler(Exception exception) {
        return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception exception) {
        return handleException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorResponse> handleEntityConflictException(Exception exception) {
        return handleException(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(exception = { AccountException.class, TransactionException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception exception, HttpStatus status) {
        String message = exception.getMessage() != null ? exception.getMessage() : EMPTY_STRING;
        ErrorResponse body = new ErrorResponse(message);
        log.error("Error with status: {} and message: {}", status, message, exception);
        return new ResponseEntity<>(body, status);
    }
}
