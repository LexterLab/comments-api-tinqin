package com.tinqinacademy.comments.api.operations.getroomcomments;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GetRoomCommentsInput {
    private String roomId;
}
