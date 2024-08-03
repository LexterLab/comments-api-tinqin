package com.tinqinacademy.comments.api.operations.leaveroomcomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LeaveRoomCommentOutput implements OperationOutput {
    private UUID id;
}
