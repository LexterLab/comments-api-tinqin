package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.GetRoomCommentsService;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.roomcomment.RoomCommentOutput;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetRoomCommentsServiceImpl implements GetRoomCommentsService {
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
