package com.practise.grower.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException ex){
        ErrorResponse error = new ErrorResponse(ex.getStatusCode(),
                ex.getMessage(), LocalDateTime.now(),
                ex.getStackTrace()[0].getMethodName());
        return ResponseEntity.status(ex.getStatusCode()).body(error);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map> ValidationExceptionHandler(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->
                errors.put(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.status(ex.getStatusCode()).body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> RuntimeExceptionHandler(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), LocalDateTime.now(),
                ex.getStackTrace()[0].getMethodName());

        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

}
