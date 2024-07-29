package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;

public interface DeleteRoomCommentService {
    DeleteRoomCommentOutput deleteRoomComment(DeleteRoomCommentInput input);
}
