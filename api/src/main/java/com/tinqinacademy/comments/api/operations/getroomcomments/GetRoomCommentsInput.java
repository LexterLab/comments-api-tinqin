package com.tinqinacademy.comments.api.operations.getroomcomments;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomCommentsInput {
    private UUID roomId;
}
