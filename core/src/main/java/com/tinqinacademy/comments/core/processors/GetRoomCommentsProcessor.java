package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomComments;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.RoomCommentOutput;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.Match;

@Service
@Slf4j
public class GetRoomCommentsProcessor extends BaseProcessor implements GetRoomComments {
    private final CommentRepository commentRepository;

    public GetRoomCommentsProcessor(ConversionService conversionService, Validator validator, CommentRepository commentRepository) {
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorOutput, GetRoomCommentsOutput> process(GetRoomCommentsInput input) {
        log.info("Start getRoomComments {}", input);

        return Try.of(() -> {
            List<RoomCommentOutput> roomComments = fetchRoomComments(input);

            GetRoomCommentsOutput output = GetRoomCommentsOutput.builder()
                    .roomComments(roomComments)
                    .build();

            log.info("End getRoomComments {}", output);

            return output;
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        defaultCase(throwable)
                ));
    }


    private List<RoomCommentOutput> fetchRoomComments(GetRoomCommentsInput input) {
        log.info("Start fetchRoomComments {}", input);

        List<RoomCommentOutput> roomComments = commentRepository.findAllByRoomId(UUID.fromString(input.getRoomId())).stream()
                .map(comment -> conversionService.convert(comment, RoomCommentOutput.class))
                .toList();

        log.info("End fetchRoomComments {}", roomComments);
        return roomComments;
    }
}
