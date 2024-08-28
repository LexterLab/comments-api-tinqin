package com.tinqinacademy.comments.restexport;

import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
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
import java.lang.String;

@Headers("Content-Type: application/json")
public interface CommentClient {
  @RequestLine("GET /api/v1/hotel/{roomId}/comment")
  GetRoomCommentsOutput getRoomComments(@Param("roomId") String roomId);

  @RequestLine("POST /api/v1/hotel/{roomId}/comment")
  LeaveRoomCommentOutput leaveRoomComment(@Param("roomId") String roomId,
      LeaveRoomCommentInput input);

  @RequestLine("PATCH /api/v1/hotel/comment/{commentId}")
  EditCommentOutput editComment(@Param("commentId") String commentId, EditCommentInput input);

  @RequestLine("PUT /api/v1/system/comment/{commentId}")
  EditUserCommentOutput editUserComment(@Param("commentId") String commentId,
      EditUserCommentInput input);

  @RequestLine("DELETE /api/v1/system/comment/{commentId}")
  DeleteRoomCommentOutput deleteUserComment(@Param("commentId") String commentId);
}
