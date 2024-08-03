package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.persistence.models.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentRepository;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveRoomCommentProcessorTest {

    @InjectMocks
    private LeaveRoomCommentProcessor leaveRoomComment;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private HotelClient hotelClient;

    @Mock
    private Validator validator;

    @Test
    void shouldLeaveRoomComment() {
        LeaveRoomCommentInput input = LeaveRoomCommentInput
                .builder()
                .roomId(String.valueOf(UUID.randomUUID()))
                .content("Content")
                .firstName("FirstName")
                .lastName("LastName")
                .build();

        GetRoomOutput roomOutput = GetRoomOutput
                .builder()
                .id(UUID.fromString(input.getRoomId()))
                .build();

        Comment comment = Comment
                .builder()
                .id(UUID.randomUUID())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .roomId(UUID.fromString(input.getRoomId()))
                .publishDate(LocalDateTime.now())
                .build();

        when(hotelClient.getRoomById(input.getRoomId())).thenReturn(roomOutput);
        when(conversionService.convert(input, Comment.class)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);


       Either<ErrorOutput, LeaveRoomCommentOutput> output = leaveRoomComment.process(input);

       assertEquals(comment.getId(), output.get().getId());
    }
}