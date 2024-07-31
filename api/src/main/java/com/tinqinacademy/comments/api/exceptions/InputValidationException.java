package com.tinqinacademy.comments.api.exceptions;

import com.tinqinacademy.comments.api.error.Error;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class InputValidationException extends RuntimeException {
    private List<Error> errors;
}
