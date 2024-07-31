package com.tinqinacademy.comments.api.operations.getroomcomments;

import com.tinqinacademy.comments.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomCommentsInput implements OperationInput {
    private UUID roomId;
}
