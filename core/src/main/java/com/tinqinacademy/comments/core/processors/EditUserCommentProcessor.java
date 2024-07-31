package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserComment;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class EditUserCommentProcessor extends BaseProcessor implements EditUserComment {
    private final CommentRepository commentRepository;

    public EditUserCommentProcessor(ConversionService conversionService, Validator validator, CommentRepository commentRepository) {
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorOutput, EditUserCommentOutput> process (EditUserCommentInput input) {
        log.info("Start editUserComment {}", input);

         return   Try.of(() -> {
            Comment comment = fetchCommentFromInput(input);

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
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        validatorCase(throwable),
                        defaultCase(throwable)
                ));

    }

    private Comment fetchCommentFromInput(EditUserCommentInput input) {
        log.info("Start fetchCommentFromInput {}", input);

        Comment comment = commentRepository.findById(input.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", input.getCommentId().toString()));

        log.info("End fetchCommentFromInput {}", comment);
        return comment;
    }
}
