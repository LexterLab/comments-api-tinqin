package com.tinqinacademy.comments.core.converters.impl;

import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.core.converters.AbstractConverter;
import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EditUserCommentInputToComment extends AbstractConverter<EditUserCommentInput, Comment> {
    @Override
    protected Class<Comment> getTargetClass() {
        return Comment.class;
    }

    @Override
    protected Comment doConvert(EditUserCommentInput source) {
        Comment comment = Comment
                .builder()
                .content(source.getContent())
                .id(UUID.fromString(source.getCommentId()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .lastEditedBy(UUID.fromString(source.getUserId()))
                .roomId(UUID.fromString(source.getRoomId()))
                .build();

        return comment;
    }
}
