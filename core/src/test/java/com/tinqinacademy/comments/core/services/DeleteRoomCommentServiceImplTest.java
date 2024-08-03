package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteRoomCommentServiceImplTest {

    @InjectMocks
    private DeleteRoomCommentServiceImpl deleteRoomCommentService;

    @Mock
    private CommentRepository commentRepository;


    @Test
    void shouldDeleteRoomComment() {
        DeleteRoomCommentInput input = DeleteRoomCommentInput
                .builder()
                .commentId(UUID.randomUUID())
                .build();
        Comment comment = Comment
                .builder()
                .id(input.getCommentId())
                .build();

        when(commentRepository.findById(input.getCommentId())).thenReturn(Optional.of(comment));

        deleteRoomCommentService.deleteRoomComment(input);

        verify(commentRepository).delete(comment);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingUnExistingComment() {
        DeleteRoomCommentInput input = DeleteRoomCommentInput
                .builder()
                .commentId(UUID.randomUUID())
                .build();

        when(commentRepository.findById(input.getCommentId())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> deleteRoomCommentService.deleteRoomComment(input));
    }
}