package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomComment;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import feign.Feign;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
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
public class LeaveRoomCommentProcessor extends BaseProcessor implements LeaveRoomComment {
    private final CommentRepository commentRepository;

    public LeaveRoomCommentProcessor(ConversionService conversionService, Validator validator, CommentRepository commentRepository) {
        super(conversionService, validator);
        this.commentRepository = commentRepository;
    }


    @Override
    public Either<ErrorOutput, LeaveRoomCommentOutput> process(LeaveRoomCommentInput input) {
        log.info("Start leaveRoomComment {}", input );

        return Try.of(() -> {
            String  roomOutput = fetchRoomFromInput(input);

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
                        customCase(throwable, HttpStatus.NOT_FOUND, ResourceNotFoundException.class),
                        validatorCase(throwable),
                        defaultCase(throwable)
                ));

    }

    private String fetchRoomFromInput(LeaveRoomCommentInput input) {
        log.info("Start fetchRoomFromInput {}", input );
        HotelClient hotelClient = Feign.builder()
                .encoder(new SpringFormEncoder())
                .decoder(new JacksonDecoder())
                .target(HotelClient.class, "http://localhost:8080/api/v1");

        GetRoomOutput output = hotelClient.getRoomById(input.getRoomId());
        UUID id = hotelClient.getRoomById(input.getRoomId()).getId();
        log.info("End fetchRoomFromInput {}", id );
        return id.toString();
    }
}
