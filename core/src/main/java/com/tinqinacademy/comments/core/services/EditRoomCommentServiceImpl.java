package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.EditRoomCommentService;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditRoomCommentServiceImpl implements EditRoomCommentService {
    private final CommentRepository commentRepository;

    @Override
    public EditCommentOutput editRoomComment(EditCommentInput input) {
        log.info("Start editRoomComment {}", input);
        Comment comment = commentRepository.findById(input.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getId().toString()));

        comment.setContent(input.getContent());

        commentRepository.save(comment);

        EditCommentOutput output = EditCommentOutput
                .builder()
                .id(comment.getId())
                .build();

        log.info("End editRoomComment {}", output);

        return output;
    }
}
