package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditUserCommentServiceImplTest {

    @InjectMocks
    private EditUserCommentServiceImpl service;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;


    @Test
    void shouldEditUserComment() {
        EditUserCommentInput input = EditUserCommentInput
                .builder()
                .commentId(UUID.randomUUID())
                .firstName("First Name")
                .lastName("Last Name")
                .content("Content")
                .build();

        Comment comment = Comment
                .builder()
                .id(input.getCommentId())
                .roomId(UUID.randomUUID())
                .build();

        Comment editedComment = Comment
                .builder()
                .id(comment.getId())
                .roomId(comment.getRoomId())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .lastEditedBy("Admin")
                .build();

        when(commentRepository.findById(input.getCommentId())).thenReturn(Optional.of(comment));
        when(conversionService.convert(input, Comment.class)).thenReturn(editedComment);
        when(commentRepository.save(editedComment)).thenReturn(editedComment);

        EditUserCommentOutput expectedOutput = EditUserCommentOutput
                .builder()
                .id(editedComment.getId())
                .build();

        EditUserCommentOutput output = service.editUserComment(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenEditingNonExistentUserComment() {
        EditUserCommentInput input = EditUserCommentInput
                .builder()
                .commentId(UUID.randomUUID())
                .firstName("First Name")
                .lastName("Last Name")
                .content("Content")
                .build();

        when(commentRepository.findById(input.getCommentId())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.editUserComment(input));
    }
}