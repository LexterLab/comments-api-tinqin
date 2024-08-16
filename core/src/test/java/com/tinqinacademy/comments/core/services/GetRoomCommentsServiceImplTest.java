package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.roomcomment.RoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
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
class GetRoomCommentsServiceImplTest {

    @InjectMocks
    private GetRoomCommentsServiceImpl getRoomCommentsServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;


    @Test
    void shouldReturnAllRoomComments() {
        GetRoomCommentsInput input = GetRoomCommentsInput
                .builder()
                .roomId(UUID.randomUUID())
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.randomUUID())
                .roomId(input.getRoomId())
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

        when(commentRepository.findAllByRoomId(input.getRoomId())).thenReturn(List.of(comment));
        when(conversionService.convert(comment, RoomCommentOutput.class)).thenReturn(roomCommentOutput);

        GetRoomCommentsOutput output = getRoomCommentsServiceImpl.getRoomComments(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }
}