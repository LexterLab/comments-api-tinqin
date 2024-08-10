package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditRoomCommentProcessorTest {

    @InjectMocks
    private EditRoomCommentProcessor editRoomComment;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private Validator validator;

    @Test
    void shouldEditRoomComment() {
        EditCommentInput input = EditCommentInput
                .builder()
                .id(String.valueOf(UUID.randomUUID()))
                .content("Random content")
                .userId(UUID.randomUUID().toString())
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.fromString(input.getId()))
                .userId(UUID.fromString(input.getUserId()))
                .content("Content")
                .build();

        Comment updatedComment = Comment
                .builder()
                .id(comment.getId())
                .lastEditedBy(UUID.fromString(input.getUserId()))
                .content(input.getContent())
                .build();


        when(commentRepository.findById(UUID.fromString(input.getId()))).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(updatedComment);

        EditCommentOutput expectedOutput = EditCommentOutput
                .builder()
                .id(comment.getId())
                .build();

        Either<ErrorOutput, EditCommentOutput> output = editRoomComment.process(input);

        assertEquals(expectedOutput.toString(), output.get().toString());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenEditingNonExistingRoomComment() {
        EditCommentInput input = EditCommentInput
                .builder()
                .id(String.valueOf(UUID.randomUUID()))
                .content("Random content")
                .build();

        ErrorOutput expectedOutput = ErrorOutput.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        when(commentRepository.findById(UUID.fromString(input.getId()))).thenThrow(ResourceNotFoundException.class);


        Either<ErrorOutput, EditCommentOutput> output = editRoomComment.process(input);

        assertEquals(expectedOutput.getStatusCode().toString(), output.getLeft().getStatusCode().toString());
    }
}