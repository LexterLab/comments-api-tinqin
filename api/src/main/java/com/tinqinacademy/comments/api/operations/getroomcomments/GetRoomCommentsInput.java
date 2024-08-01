package com.tinqinacademy.comments.api.operations.getroomcomments;

import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomCommentsInput implements OperationInput {
    @NotBlank(message = "Field roomId cannot be blank")
    @UUID(message = "Field roomId must be UUID")
    private String roomId;
}
