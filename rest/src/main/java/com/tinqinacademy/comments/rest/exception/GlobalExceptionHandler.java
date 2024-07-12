package com.tinqinacademy.comments.rest.exception;

import com.tinqinacademy.comments.api.contracts.ErrorHandlerService;
import com.tinqinacademy.comments.api.operations.error.ErrorDetails;
import com.tinqinacademy.comments.rest.controllers.HotelController;
import com.tinqinacademy.comments.rest.controllers.SystemController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {HotelController.class, SystemController.class})
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorHandlerService errorHandlerService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                              HttpServletRequest request) {
        log.error("Request {} raised MethodArgumentNotValidException {}", request.getRequestURL(), ex.getBody());

        return new ResponseEntity<>(errorHandlerService.handle(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handlesServerErrors(Exception ex, HttpServletRequest request) {
        log.error("Request {} raised ServerErrors {}", request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(errorHandlerService.handle(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}