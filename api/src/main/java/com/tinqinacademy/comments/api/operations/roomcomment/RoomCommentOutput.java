package com.tinqinacademy.comments.api.operations.roomcomment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomCommentOutput {
    @Schema(example = "UUID")
    private String id;
    @Schema(example = "George")
    private String firstName;
    @Schema(example = "Russell")
    private String lastName;
    @Schema(example = "Insane room 10/10")
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditedDate;
    @Schema(example = "George Russell")
    private String lastEditedBy;
}
