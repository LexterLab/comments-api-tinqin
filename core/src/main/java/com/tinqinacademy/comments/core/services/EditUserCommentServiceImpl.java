package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.EditUserCommentService;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EditUserCommentServiceImpl implements EditUserCommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public EditUserCommentOutput editUserComment(EditUserCommentInput input) {
        log.info("Start editUserComment {}", input);
        Comment comment = commentRepository.findById(input.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getCommentId().toString()));

        Comment editedComment = conversionService.convert(input, Comment.class);
        editedComment.setLastEditedBy("Admin");
        editedComment.setRoomId(comment.getRoomId());

        commentRepository.save(editedComment);

        EditUserCommentOutput output = EditUserCommentOutput
                .builder()
                .id(editedComment.getId())
                .build();

        log.info("End editUserComment {}", output);
        return output;
    }
}
