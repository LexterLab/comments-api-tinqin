package com.tinqinacademy.comments.api.operations.editcomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EditCommentOutput implements OperationOutput {
    @Schema(example = "UUID")
    private UUID id;
}
