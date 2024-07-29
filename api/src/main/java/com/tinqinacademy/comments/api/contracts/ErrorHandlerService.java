package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.error.ErrorOutput;

public interface ErrorHandlerService {
    ErrorOutput handle(Exception exception);
}
