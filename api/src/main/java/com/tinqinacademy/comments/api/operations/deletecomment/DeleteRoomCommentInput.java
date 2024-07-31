package com.tinqinacademy.comments.api.operations.deletecomment;

import com.tinqinacademy.comments.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomCommentInput implements OperationInput {
    private UUID commentId;
}
