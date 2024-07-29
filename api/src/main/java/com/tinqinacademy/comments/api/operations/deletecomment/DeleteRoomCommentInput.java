package com.tinqinacademy.comments.api.operations.deletecomment;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomCommentInput {
    private UUID commentId;
}
