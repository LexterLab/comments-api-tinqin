package com.tinqinacademy.comments.core.converters.impl;

import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.core.converters.AbstractConverter;
import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LeaveRoomCommentInputToComment extends AbstractConverter<LeaveRoomCommentInput, Comment> {
    @Override
    protected Class<Comment> getTargetClass() {
        return Comment.class;
    }

    @Override
    protected Comment doConvert(LeaveRoomCommentInput source) {
        Comment comment = Comment
                .builder()
                .roomId(UUID.fromString(source.getRoomId()))
                .content(source.getContent())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();

        return comment;
    }
}
