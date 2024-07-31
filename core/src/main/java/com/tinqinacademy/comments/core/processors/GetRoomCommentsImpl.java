package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomComments;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.RoomCommentOutput;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetRoomCommentsImpl implements GetRoomComments {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public GetRoomCommentsOutput getRoomComments(GetRoomCommentsInput input) {
        log.info("Start getRoomComments {}", input);

        List<RoomCommentOutput> roomComments = commentRepository.findAllByRoomId(input.getRoomId()).stream()
                .map(comment -> conversionService.convert(comment, RoomCommentOutput.class))
                .toList();

        GetRoomCommentsOutput output = GetRoomCommentsOutput.builder()
                .roomComments(roomComments)
                .build();

        log.info("End getRoomComments {}", output);

        return output;
    }
}
