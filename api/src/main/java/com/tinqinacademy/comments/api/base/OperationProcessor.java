package com.tinqinacademy.comments.api.base;

import com.tinqinacademy.comments.api.operations.error.ErrorOutput;
import io.vavr.control.Either;

public interface OperationProcessor <I extends OperationInput, O extends OperationOutput> {
    Either<ErrorOutput, O> process(I input);
}
