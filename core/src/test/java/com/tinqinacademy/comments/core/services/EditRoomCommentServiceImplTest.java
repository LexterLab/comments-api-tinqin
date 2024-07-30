package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditRoomCommentServiceImplTest {

    @InjectMocks
    private EditRoomCommentServiceImpl editRoomCommentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void shouldEditRoomComment() {
        EditCommentInput input = EditCommentInput
                .builder()
                .id(UUID.randomUUID())
                .content("Random content")
                .build();

        Comment comment = Comment
                .builder()
                .id(input.getId())
                .content("Content")
                .build();

        when(commentRepository.findById(input.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        EditCommentOutput expectedOutput = EditCommentOutput
                .builder()
                .id(comment.getId())
                .build();

        EditCommentOutput output = editRoomCommentService.editRoomComment(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenEditingNonExistingRoomComment() {
        EditCommentInput input = EditCommentInput
                .builder()
                .id(UUID.randomUUID())
                .content("Random content")
                .build();

        when(commentRepository.findById(input.getId())).thenThrow(ResourceNotFoundException.class);

       assertThrows(ResourceNotFoundException.class, () -> editRoomCommentService.editRoomComment(input));
    }
}