package com.argus.minesweeperrest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ErrorResponseException.class})
    private ResponseEntity<Object> handleException(ErrorResponseException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }

}
