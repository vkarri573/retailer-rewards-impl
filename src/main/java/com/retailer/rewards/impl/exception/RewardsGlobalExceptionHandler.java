package com.retailer.rewards.impl.exception;

import com.retailer.rewards.impl.model.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class RewardsGlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDataNotFound(DataNotFoundException exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request){
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), "Validation Failed",
                exception.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse prepareExceptionResponse(Exception exception, WebRequest request) {
        log.error("Exception Occurred: ", exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
                request.getDescription(false));
        return exceptionResponse;
    }
}
