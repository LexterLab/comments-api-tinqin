package com.tinqinacademy.comments.api.operations.leaveroomcomment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LeaveRoomCommentOutput {
    private UUID id;
}
