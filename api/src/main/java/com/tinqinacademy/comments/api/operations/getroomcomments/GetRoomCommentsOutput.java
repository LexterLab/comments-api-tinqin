package com.tinqinacademy.comments.api.operations.getroomcomments;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetRoomCommentsOutput implements OperationOutput {
    List<RoomCommentOutput> roomComments;
}
