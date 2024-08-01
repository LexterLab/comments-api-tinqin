package com.tinqinacademy.comments.core.converters.impl;

import com.tinqinacademy.comments.api.operations.getroomcomments.RoomCommentOutput;
import com.tinqinacademy.comments.core.converters.AbstractConverter;
import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentToRoomCommentOutput extends AbstractConverter<Comment, RoomCommentOutput> {

    @Override
    protected Class<RoomCommentOutput> getTargetClass() {
        return RoomCommentOutput.class;
    }

    @Override
    protected RoomCommentOutput doConvert(Comment source) {
        RoomCommentOutput output = RoomCommentOutput
                .builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .content(source.getContent())
                .publishDate(source.getPublishDate())
                .lastEditedBy(source.getLastEditedBy())
                .lastEditedDate(source.getLastEditedDate())
                .build();

        return output;
    }
}
