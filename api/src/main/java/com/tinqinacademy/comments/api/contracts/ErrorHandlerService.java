package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.error.ErrorDetails;

public interface ErrorHandlerService {
    ErrorDetails handle(Exception exception);
}
