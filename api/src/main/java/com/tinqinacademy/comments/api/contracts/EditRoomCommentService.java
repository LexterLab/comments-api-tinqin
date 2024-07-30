package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;

public interface EditRoomCommentService {
    EditCommentOutput editRoomComment(EditCommentInput input);
}
