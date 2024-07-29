package com.tinqinacademy.comments.api.operations.leaveroomcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LeaveRoomCommentInput {
    @JsonIgnore
    private UUID roomId;
    @Schema(example = "George")
    @NotBlank(message = "Field firstName cannot be blank")
    @Size(min = 2, max = 30, message = "Field firstName must be between 2-30 characters")
    private String firstName;
    @Schema(example = "Russell")
    @NotBlank(message = "Field lastName cannot be blank")
    @Size(min = 2, max = 30, message = "Field lastName must be between 2-30 characters")
    private String lastName;
    @Schema(example = "This room is sick bro!")
    @NotBlank(message = "Field content cannot not be blank")
    @Size(min = 5, max = 500, message = "Field content must be between 5-500 characters")
    private String content;
}
