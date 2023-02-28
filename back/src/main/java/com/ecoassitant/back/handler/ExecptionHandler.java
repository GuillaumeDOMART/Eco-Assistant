package com.ecoassitant.back.handler;

import com.ecoassitant.back.exception.NoSuchElementInDataBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExecptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementInDataBase.class)
    public ResponseEntity<Object> noSuchElementInDataBaseHandler(){

        return new ResponseEntity<>(Map.of("error", "not found"),HttpStatus.NOT_FOUND);
    }
}
