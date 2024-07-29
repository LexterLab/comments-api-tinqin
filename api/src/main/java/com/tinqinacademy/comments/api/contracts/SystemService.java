package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;

public interface SystemService {
    DeleteRoomCommentOutput deleteRoomComment(DeleteRoomCommentInput input);
}
