package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.exceptions.CommentUserMismatchException;
import com.tinqinacademy.comments.api.operations.editcomment.EditRoomComment;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
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

@Slf4j
@Service
public class EditRoomCommentProcessor extends BaseProcessor implements EditRoomComment {
    private final CommentRepository commentRepository;

    public EditRoomCommentProcessor(ConversionService conversionService, Validator validator, CommentRepository commentRepository) {
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorOutput,EditCommentOutput> process(EditCommentInput input) {
        log.info("Start editRoomComment {}", input);

       return Try.of(() -> {
           validateInput(input);
           Comment comment = fetchCommentFromInput(input);

           checkIfUserMatchesComment(input, comment);

           comment.setContent(input.getContent());
           comment.setLastEditedBy(UUID.fromString(input.getUserId()));

           commentRepository.save(comment);

           EditCommentOutput output = EditCommentOutput
                   .builder()
                   .id(comment.getId())
                   .build();

           log.info("End editRoomComment {}", output);

           return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       validatorCase(throwable),
                       customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                       customCase(throwable, HttpStatus.BAD_REQUEST, CommentUserMismatchException.class),
                       defaultCase(throwable)
       ));

    }

    private Comment fetchCommentFromInput(EditCommentInput input) {
        log.info("Start fetchCommentFromInput {}", input);

        Comment comment = commentRepository.findById(UUID.fromString(input.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getId()));

        log.info("End fetchCommentFromInput {}", comment);
        return comment;
    }

    private void checkIfUserMatchesComment(EditCommentInput input, Comment comment) {
        log.info("Start checkIfUserMatchesComment {}", input);

        if (!comment.getUserId().equals(UUID.fromString(input.getUserId()))) {
            throw new CommentUserMismatchException(input.getId(), comment.getUserId().toString());
        }

    }
}
