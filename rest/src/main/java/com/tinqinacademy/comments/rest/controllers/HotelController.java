package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.contracts.HotelService;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.rest.utils.PathConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Hotel REST APIs")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping(PathConstants.GET_ROOM_COMMENTS)
    public ResponseEntity<GetRoomCommentsOutput> getRoomComments(@PathVariable String roomId) {
        GetRoomCommentsInput input  = GetRoomCommentsInput.builder()
                .roomId(roomId)
                .build();
        return new ResponseEntity<>(hotelService.getRoomComments(input), HttpStatus.OK);
    }

    @PostMapping(PathConstants.LEAVE_ROOM_COMMENT)
    public ResponseEntity<LeaveRoomCommentOutput> leaveRoomComment(@PathVariable String roomId,
                                                                  @Valid LeaveRoomCommentInput input) {
        input = LeaveRoomCommentInput.builder()
                .roomId(roomId)
                .content(input.getContent())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .build();

        return new ResponseEntity<>(hotelService.leaveRoomComment(input), HttpStatus.CREATED);
    }

    @PatchMapping(PathConstants.EDIT_COMMENT)
    public ResponseEntity<EditCommentOutput> editComment(@PathVariable String commentId,
                                                         @Valid EditCommentInput input) {
        input = EditCommentInput.builder()
                .id(commentId)
                .content(input.getContent())
                .build();

        return new ResponseEntity<>(hotelService.editRoomComment(input), HttpStatus.OK);
    }

}
