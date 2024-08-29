package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.error.ErrorOutput;
import com.tinqinacademy.comments.api.operations.editcomment.EditRoomComment;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomComments;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomComment;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editcomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentInput;
import com.tinqinacademy.comments.api.operations.leaveroomcomment.LeaveRoomCommentOutput;
import com.tinqinacademy.comments.api.RestAPIRoutes;
import com.tinqinacademy.restexportprocessor.main.RestExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Hotel REST APIs")
public class HotelController extends BaseController {

    private final GetRoomComments getRoomComments;
    private final LeaveRoomComment leaveRoomComment;
    private final EditRoomComment editRoomComment;

    public HotelController(GetRoomComments getRoomComments, LeaveRoomComment leaveRoomComment, EditRoomComment editRoomComment) {
        this.getRoomComments = getRoomComments;
        this.leaveRoomComment = leaveRoomComment;
        this.editRoomComment = editRoomComment;
    }

    @Operation(
            summary = "Get room comments Rest API",
            description = "Get room comments Rest API is used for retrieving room's comments by roomId"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetRoomCommentsOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    }
    )
    @RestExport
    @GetMapping(RestAPIRoutes.GET_ROOM_COMMENTS)
    public ResponseEntity<?> getRoomComments(@PathVariable String roomId) {
        GetRoomCommentsInput input  = GetRoomCommentsInput.builder()
                .roomId(roomId)
                .build();
        Either<ErrorOutput, GetRoomCommentsOutput> output = getRoomComments.process(input);
        return handleOutput(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Leave room comment Rest API",
            description = "Leave room comment Rest API is used for leaving a room comment by roomId"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP STATUS 201 CREATED", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LeaveRoomCommentOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "HTT STATUS 401 UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    })
    @RestExport
    @PostMapping(RestAPIRoutes.LEAVE_ROOM_COMMENT)
    public ResponseEntity<?> leaveRoomComment(@PathVariable String roomId, @RequestBody LeaveRoomCommentInput input) {
        input = LeaveRoomCommentInput.builder()
                .roomId(roomId)
                .userId(input.getUserId())
                .content(input.getContent())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .build();

        Either<ErrorOutput,LeaveRoomCommentOutput> output = leaveRoomComment.process(input);

        return handleOutput(output, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edit room comment Rest API",
            description = "Edit room comment Rest API is used for users to edit their room comments by commentId"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 SUCCESS", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EditCommentOutput.class))
            ),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "HTT STATUS 401 UNAUTHORIZED"),
            @ApiResponse(responseCode = "404", description = "HTTP STATUS 404 NOT FOUND")
    })
    @RestExport
    @PatchMapping(RestAPIRoutes.EDIT_COMMENT)
    public ResponseEntity<?> editComment(@PathVariable String commentId, @RequestBody EditCommentInput input) {
        input = EditCommentInput.builder()
                .id(commentId)
                .userId(input.getUserId())
                .content(input.getContent())
                .build();

        Either<ErrorOutput, EditCommentOutput> output = editRoomComment.process(input);
        return handleOutput(output, HttpStatus.OK);
    }

}
