package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveRoomCommentServiceImplTest {

    @InjectMocks
    private LeaveRoomCommentServiceImpl leaveRoomCommentServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;

    @Test
    void shouldLeaveRoomComment() {
        LeaveRoomCommentInput input = LeaveRoomCommentInput
                .builder()
                .roomId(UUID.randomUUID())
                .content("Content")
                .firstName("FirstName")
                .lastName("LastName")
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.randomUUID())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .roomId(input.getRoomId())
                .publishDate(LocalDateTime.now())
                .build();

        when(conversionService.convert(input, Comment.class)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);

        LeaveRoomCommentOutput output = leaveRoomCommentServiceImpl.leaveRoomComment(input);

        assertEquals(comment.getId(), output.getId());
    }
}