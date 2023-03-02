package com.ecoassitant.back.handler;

import com.ecoassitant.back.exception.NoSuchElementInDataBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

/**
 * Exception Handler
 */
@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler for NoSuchElementInDataBaseException
     * @return ResponseEntity 404
     */
    @ExceptionHandler(NoSuchElementInDataBaseException.class)
    public ResponseEntity<Object> noSuchElementInDataBaseHandler(){

        return new ResponseEntity<>(Map.of("error", "not found"),HttpStatus.NOT_FOUND);
    }
}
