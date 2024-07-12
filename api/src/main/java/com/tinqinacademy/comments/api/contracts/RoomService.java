package com.tinqinacademy.comments.api.contracts;

import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;

public interface RoomService {
    GetRoomCommentsOutput getRoomComments(GetRoomCommentsInput input);
}
