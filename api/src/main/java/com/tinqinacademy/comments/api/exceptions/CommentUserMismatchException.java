package com.tinqinacademy.comments.api.exceptions;

import com.tinqinacademy.comments.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUserMismatchException extends RuntimeException {
    private final String userId;
    private final String commentId;
    private final String message;

    public CommentUserMismatchException(String userId, String commentId) {
        this.userId = userId;
        this.commentId = commentId;
        this.message = String.format(Messages.COMMENT_USER_MISMATCH, commentId, userId);
    }
}
