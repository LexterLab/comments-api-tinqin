package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.DeleteRoomCommentService;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteRoomCommentServiceImpl implements DeleteRoomCommentService {
    private final CommentRepository commentRepository;

    @Override
    public DeleteRoomCommentOutput deleteRoomComment(DeleteRoomCommentInput input) {
        log.info("Start deleteRoomComment {}", input);

        Comment comment = commentRepository.findById(input.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getCommentId().toString()));

        commentRepository.delete(comment);

        DeleteRoomCommentOutput output = DeleteRoomCommentOutput.builder().build();

        log.info("End deleteRoomComment {}", output);
        return output;
    }





}
