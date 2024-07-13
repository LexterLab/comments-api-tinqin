package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.contracts.HotelService;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.rest.utils.PathConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get room comments Rest API",
            description = "Get room comments Rest API is used for retrieving room's comments by roomId"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @GetMapping(PathConstants.GET_ROOM_COMMENTS)
    public ResponseEntity<GetRoomCommentsOutput> getRoomComments(@PathVariable String roomId) {
        GetRoomCommentsInput input  = GetRoomCommentsInput.builder()
                .roomId(roomId)
                .build();
        return new ResponseEntity<>(hotelService.getRoomComments(input), HttpStatus.OK);
    }

    @Operation(
            summary = "Leave room comment Rest API",
            description = "Leave room comment Rest API is used for leaving a room comment by roomId"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "HTT STATUS 401 UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @PostMapping(PathConstants.LEAVE_ROOM_COMMENT)
    public ResponseEntity<LeaveRoomCommentOutput> leaveRoomComment(@PathVariable String roomId,
                                                                  @Valid @RequestBody LeaveRoomCommentInput input) {
        input = LeaveRoomCommentInput.builder()
                .roomId(roomId)
                .content(input.getContent())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .build();

        return new ResponseEntity<>(hotelService.leaveRoomComment(input), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edit room comment Rest API",
            description = "Edit room comment Rest API is used for users to edit their room comments by commentId"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "HTTP STATUS 200 SUCCESS"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "HTT STATUS 401 UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "HTTP STATUS 500 INTERNAL SERVER ERROR")
    }
    )
    @PatchMapping(PathConstants.EDIT_COMMENT)
    public ResponseEntity<EditCommentOutput> editComment(@PathVariable String commentId,
                                                         @Valid @RequestBody EditCommentInput input) {
        input = EditCommentInput.builder()
                .id(commentId)
                .content(input.getContent())
                .build();

        return new ResponseEntity<>(hotelService.editRoomComment(input), HttpStatus.OK);
    }

}
