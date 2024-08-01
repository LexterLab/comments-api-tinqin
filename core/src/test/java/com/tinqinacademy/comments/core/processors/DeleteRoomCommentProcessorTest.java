package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteRoomCommentProcessorTest {

    @InjectMocks
    private DeleteRoomCommentProcessor deleteRoomComment;

    @Mock
    private CommentRepository commentRepository;


    @Test
    void shouldDeleteRoomComment() {
        DeleteRoomCommentInput input = DeleteRoomCommentInput
                .builder()
                .commentId(UUID.randomUUID().toString())
                .build();
        Comment comment = Comment
                .builder()
                .id(UUID.fromString(input.getCommentId()))
                .build();

        when(commentRepository.findById(UUID.fromString(input.getCommentId()))).thenReturn(Optional.of(comment));

        deleteRoomComment.process(input);

        verify(commentRepository).delete(comment);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingUnExistingComment() {
        DeleteRoomCommentInput input = DeleteRoomCommentInput
                .builder()
                .commentId(String.valueOf(UUID.randomUUID()))
                .build();

        ErrorOutput expectedOutput = ErrorOutput.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        when(commentRepository.findById(UUID.fromString(input.getCommentId()))).thenThrow(ResourceNotFoundException.class);

        Either<ErrorOutput, DeleteRoomCommentOutput> actualOutput = deleteRoomComment.process(input);

        assertEquals(expectedOutput.getStatusCode().toString(), actualOutput.getLeft().getStatusCode().toString());

    }
}