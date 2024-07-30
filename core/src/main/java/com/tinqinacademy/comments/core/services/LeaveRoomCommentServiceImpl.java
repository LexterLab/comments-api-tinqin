package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.LeaveRoomCommentService;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveRoomCommentServiceImpl implements LeaveRoomCommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public LeaveRoomCommentOutput leaveRoomComment(LeaveRoomCommentInput input) {
        log.info("Start leaveRoomComment {}", input );

        Comment comment = conversionService.convert(input, Comment.class);

        commentRepository.save(comment);

        LeaveRoomCommentOutput output = LeaveRoomCommentOutput
                .builder()
                .id(comment.getId())
                .build();

        log.info("End leaveRoomComment {}", output );

        return output;
    }
}
