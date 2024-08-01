package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.RoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRoomCommentsProcessorTest {

    @InjectMocks
    private GetRoomCommentsProcessor getRoomComments;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;


    @Test
    void shouldReturnAllRoomComments() {
        GetRoomCommentsInput input = GetRoomCommentsInput
                .builder()
                .roomId(String.valueOf(UUID.randomUUID()))
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.randomUUID())
                .roomId(UUID.fromString(input.getRoomId()))
                .content("Content")
                .firstName("FirstName")
                .lastName("LastName")
                .publishDate(LocalDateTime.now().minusDays(2))
                .lastEditedBy("John")
                .lastEditedDate(LocalDateTime.now())
                .build();

        RoomCommentOutput roomCommentOutput = RoomCommentOutput
                .builder()
                .id(comment.getId())
                .content(comment.getContent())
                .firstName(comment.getFirstName())
                .lastName(comment.getLastName())
                .publishDate(comment.getPublishDate())
                .lastEditedBy(comment.getLastEditedBy())
                .lastEditedDate(comment.getLastEditedDate())
                .build();

        GetRoomCommentsOutput expectedOutput = GetRoomCommentsOutput
                .builder()
                .roomComments(List.of(roomCommentOutput))
                .build();

        when(commentRepository.findAllByRoomId(UUID.fromString(input.getRoomId()))).thenReturn(List.of(comment));
        when(conversionService.convert(comment, RoomCommentOutput.class)).thenReturn(roomCommentOutput);

        Either<ErrorOutput, GetRoomCommentsOutput> output = getRoomComments.process(input);

        assertEquals(expectedOutput.toString(), output.get().toString());
    }
}