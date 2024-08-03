package com.tinqinacademy.comments.api.operations.editusercomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EditUserCommentInput implements OperationInput {
    @JsonIgnore
    @UUID(message = "Field commentId must be UUID")
    @NotBlank(message = "Field commentId must not be blank")
    private String commentId;
    @Schema(example = "201A")
    @NotBlank(message = "Field roomNo cannot be blank")
    @Size(min = 4, max = 4, message = "Field roomNo must be 4 characters")
    private String roomNo;
    @Schema(example = "Lewis")
    @NotBlank(message = "Field firstName cannot be blank")
    @Size(max = 30, min = 2, message = "Field firstName must be 2-30 characters")
    private String firstName;
    @Schema(example = "Hamilton")
    @NotBlank(message = "Field lastName cannot be blank")
    @Size(max = 30, min = 2, message = "Field lastName must be 2-30 characters")
    private String lastName;
    @Schema(example = "Greatest room ever in my life #AD")
    @NotBlank(message = "Field content cannot be blank")
    @Size(max = 500, min = 5, message = "Field content must be 5-500 characters")
    private String content;
}
