package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;

public interface SystemService {
    EditUserCommentOutput editUserComment(EditUserCommentInput input);
}
