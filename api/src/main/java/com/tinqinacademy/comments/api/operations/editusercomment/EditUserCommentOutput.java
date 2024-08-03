package com.tinqinacademy.comments.api.operations.editusercomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EditUserCommentOutput implements OperationOutput {
    private UUID id;
}
