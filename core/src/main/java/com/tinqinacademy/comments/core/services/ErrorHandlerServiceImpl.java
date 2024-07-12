package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.ErrorHandlerService;
import com.tinqinacademy.comments.api.operations.error.Error;
import com.tinqinacademy.comments.api.operations.error.ErrorDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;


@Service
public class ErrorHandlerServiceImpl implements ErrorHandlerService {
    @Override
    public ErrorDetails handle(Exception exception) {
        List<Error> errors = new ArrayList<>();

        if (exception instanceof MethodArgumentNotValidException ex) {
            errors = handleMethodArgumentNotValid(ex);
        } else {
            Error error = Error.builder().message(exception.getMessage()).build();
            errors.add(error);
        }

        ErrorDetails errorDetails = ErrorDetails.builder()
                .errors(errors)
                .build();

        return errorDetails;
    }

    private List<Error> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<Error> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(Error.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField()).build()));

        return errors;
    }
}
