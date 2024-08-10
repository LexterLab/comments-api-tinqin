package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditUserCommentProcessorTest {

    @InjectMocks
    private EditUserCommentProcessor editUserComment;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private Validator validator;

    @Test
    void shouldEditUserComment() {
        EditUserCommentInput input = EditUserCommentInput
                .builder()
                .commentId(String.valueOf(UUID.randomUUID()))
                .firstName("First Name")
                .lastName("Last Name")
                .content("Content")
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.fromString(input.getCommentId()))
                .roomId(UUID.randomUUID())
                .build();

        Comment editedComment = Comment
                .builder()
                .id(comment.getId())
                .roomId(comment.getRoomId())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .lastEditedBy(UUID.randomUUID())
                .build();

        when(commentRepository.findById(UUID.fromString(input.getCommentId()))).thenReturn(Optional.of(comment));
        when(conversionService.convert(input, Comment.class)).thenReturn(editedComment);
        when(commentRepository.save(editedComment)).thenReturn(editedComment);

        EditUserCommentOutput expectedOutput = EditUserCommentOutput
                .builder()
                .id(editedComment.getId())
                .build();

        Either<ErrorOutput,EditUserCommentOutput> output = editUserComment.process(input);

        assertEquals(expectedOutput.toString(), output.get().toString());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenEditingNonExistentUserComment() {
        EditUserCommentInput input = EditUserCommentInput
                .builder()
                .commentId(String.valueOf(UUID.randomUUID()))
                .firstName("First Name")
                .lastName("Last Name")
                .content("Content")
                .build();

        ErrorOutput expectedOutput = ErrorOutput
                .builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        when(commentRepository.findById(UUID.fromString(input.getCommentId()))).thenThrow(ResourceNotFoundException.class);

        Either<ErrorOutput,EditUserCommentOutput> output = editUserComment.process(input);
        assertEquals(expectedOutput.getStatusCode().toString(), output.getLeft().getStatusCode().toString());
    }
}