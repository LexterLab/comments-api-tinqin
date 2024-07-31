package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.base.OperationOutput;
import com.tinqinacademy.comments.api.error.ErrorOutput;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected ResponseEntity<?> handleOutput(Either<ErrorOutput, ? extends OperationOutput> output, HttpStatus status) {
        return output.fold(
                errorOutput -> new ResponseEntity<>(errorOutput, errorOutput.getStatusCode()),
                operationOutput -> new ResponseEntity<>(operationOutput, status)
        );
    }
}
