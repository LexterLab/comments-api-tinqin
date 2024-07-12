package com.tinqinacademy.comments.api.operations.editcomment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EditCommentOutput {
    @Schema(example = "UUID")
    private String id;
}
