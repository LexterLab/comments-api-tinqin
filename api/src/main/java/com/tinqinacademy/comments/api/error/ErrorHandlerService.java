package com.tinqinacademy.comments.api.error;

public interface ErrorHandlerService {
    ErrorOutput handle(Exception exception);
}
