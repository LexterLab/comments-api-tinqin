package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoom;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.vavr.API.Match;

@Service
@Slf4j
public class DeleteRoomCommentProcessor extends BaseProcessor implements DeleteRoom {
    private final CommentRepository commentRepository;

    public DeleteRoomCommentProcessor(ConversionService conversionService, Validator validator, CommentRepository commentRepository) {
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorOutput, DeleteRoomCommentOutput> process(DeleteRoomCommentInput input) {
        log.info("Start deleteRoomComment {}", input);

        return Try.of(() -> {
            Comment comment = fetchCommentFromInput(input);

            commentRepository.delete(comment);

            DeleteRoomCommentOutput output = DeleteRoomCommentOutput.builder().build();

            log.info("End deleteRoomComment {}", output);
            return output;
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        defaultCase(throwable)
                ));

    }


    private Comment fetchCommentFromInput(DeleteRoomCommentInput input) {
        log.info("Start fetchCommentFromInput {}", input);

        Comment comment = commentRepository.findById(UUID.fromString(input.getCommentId()))
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getCommentId()));

        log.info("End fetchCommentFromInput {}", comment);
        return comment;
    }


}
