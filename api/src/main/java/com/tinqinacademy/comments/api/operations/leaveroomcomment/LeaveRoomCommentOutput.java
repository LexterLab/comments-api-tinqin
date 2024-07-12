package com.tinqinacademy.comments.api.operations.leaveroomcomment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LeaveRoomCommentOutput {
    @Schema(example = "UUID")
    private String id;
}
