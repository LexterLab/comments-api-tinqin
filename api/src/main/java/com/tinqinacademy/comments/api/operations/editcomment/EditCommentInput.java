package com.tinqinacademy.comments.api.operations.editcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class EditCommentInput {
    @JsonIgnore
    private UUID id;
    @Schema(example = "This room is not as sick as i thought BRUV!!!")
    @NotBlank(message = "Field content cannot be blank")
    @Size(max = 500, min = 5, message = "Field content must be 5-500 characters")
    private String content;
}
