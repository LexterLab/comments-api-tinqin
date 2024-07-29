package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;

public interface LeaveRoomCommentService {
    LeaveRoomCommentOutput leaveRoomComment(LeaveRoomCommentInput input);
}
