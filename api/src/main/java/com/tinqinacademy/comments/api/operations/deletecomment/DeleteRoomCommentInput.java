package com.tinqinacademy.comments.api.operations.deletecomment;

import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomCommentInput implements OperationInput {
    @NotBlank(message = "Field commentId must not be blank")
    @UUID(message = "Field commentId must be UUID")
    private String commentId;
}
