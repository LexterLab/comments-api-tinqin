package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomComment;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class LeaveRoomCommentProcessor extends BaseProcessor implements LeaveRoomComment {
    private final CommentRepository commentRepository;
    public LeaveRoomCommentProcessor(ConversionService conversionService, Validator validator,
                                     CommentRepository commentRepository){
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }


    @Override
    public Either<ErrorOutput, LeaveRoomCommentOutput> process(LeaveRoomCommentInput input) {
        log.info("Start leaveRoomComment {}", input );

        return Try.of(() -> {
            validateInput(input);

            Comment comment = conversionService.convert(input, Comment.class);

            commentRepository.save(comment);

            LeaveRoomCommentOutput output = LeaveRoomCommentOutput
                    .builder()
                    .id(comment.getId())
                    .build();

            log.info("End leaveRoomComment {}", output );

            return output;
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validatorCase(throwable),
                        defaultCase(throwable)
                ));

    }
}
