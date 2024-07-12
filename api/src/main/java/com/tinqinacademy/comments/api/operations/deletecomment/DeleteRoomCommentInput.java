package com.tinqinacademy.comments.api.operations.deletecomment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomCommentInput {
    private String commentId;
}
