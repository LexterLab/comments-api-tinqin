package com.tinqinacademy.comments.core.converters.impl;

import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.core.converters.AbstractConverter;
import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.stereotype.Component;

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
                .id(source.getCommentId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();

        return comment;
    }
}
