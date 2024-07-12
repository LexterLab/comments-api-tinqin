package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;

public interface RoomService {
    GetRoomCommentsOutput getRoomComments(GetRoomCommentsInput input);
    LeaveRoomCommentOutput leaveRoomComment(LeaveRoomCommentInput input);
    EditCommentOutput editRoomComment(EditCommentInput input);

}
