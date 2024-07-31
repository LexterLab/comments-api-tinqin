package com.tinqinacademy.comments.api.exceptions;

import com.tinqinacademy.comments.api.error.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InputValidationException extends RuntimeException {
    private List<Error> errors;
}
