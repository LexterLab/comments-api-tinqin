package com.tinqinacademy.comments.restexport.restexport;

import com.tinqinacademy.comments.api.RouteExports;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface CommentClient {

    @RequestLine(RouteExports.GET_ROOM_COMMENTS)
    GetRoomCommentsOutput getRoomComments(@Param String roomId);

    @RequestLine(RouteExports.LEAVE_ROOM_COMMENT)
    LeaveRoomCommentOutput leaveRoomComment(@Param String roomId, LeaveRoomCommentInput input);

    @RequestLine(RouteExports.EDIT_COMMENT)
    EditCommentOutput editComment(@Param String commentId, EditCommentInput input);

    @RequestLine(RouteExports.EDIT_USER_COMMENT)
    EditUserCommentOutput editUserComment(@Param String commentId, EditUserCommentInput input);

    @RequestLine(RouteExports.DELETE_COMMENT)
    void deleteRoomComment(@Param String commentId);
}
