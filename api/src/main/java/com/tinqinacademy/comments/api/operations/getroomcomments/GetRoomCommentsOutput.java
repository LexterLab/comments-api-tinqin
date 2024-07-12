package com.tinqinacademy.comments.api.operations.getroomcomments;

import com.tinqinacademy.comments.api.operations.roomcomment.RoomCommentOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetRoomCommentsOutput {
    List<RoomCommentOutput> roomComments;
}
