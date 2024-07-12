package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.RoomService;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.api.operations.roomcomment.RoomCommentOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Override
    public GetRoomCommentsOutput getRoomComments(GetRoomCommentsInput input) {

        log.info("Start getRoomComments {}", input);

        RoomCommentOutput comment = RoomCommentOutput.builder()
                .id(UUID.randomUUID().toString())
                .content("Content")
                .lastEditedBy("Me")
                .lastEditedDate(LocalDateTime.now())
                .publishDate(LocalDateTime.now())
                .firstName("George")
                .lastName("Russell")
                .build();

        GetRoomCommentsOutput output = GetRoomCommentsOutput.builder()
                .roomComments(List.of(comment))
                .build();

        log.info("End getRoomComments {}", output);

        return output;
    }

    @Override
    public LeaveRoomCommentOutput leaveRoomComment(LeaveRoomCommentInput input) {

        log.info("Start leaveRoomComment {}", input);

        LeaveRoomCommentOutput output = LeaveRoomCommentOutput.builder()
                .id(UUID.randomUUID().toString())
                .build();

        log.info("End leaveRoomComment {}", output);

        return output;
    }

    @Override
    public EditCommentOutput editRoomComment(EditCommentInput input) {

        log.info("Start editRoomComment {}", input);

        EditCommentOutput output = EditCommentOutput.builder()
                .id(input.getId())
                .build();

        log.info("End editRoomComment {}", output);

        return output;
    }
}
