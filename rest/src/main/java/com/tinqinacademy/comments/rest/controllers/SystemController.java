package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.contracts.SystemService;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import com.tinqinacademy.comments.rest.utils.PathConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "System REST APIs")
public class SystemController {
    private final SystemService systemService;


    @PutMapping(PathConstants.GET_ROOM_COMMENTS)
//    @PutMapping("system/comment/{commentId}")
    public ResponseEntity<EditUserCommentOutput> editUserComment(@PathVariable String commentId,
                                                                 @Valid EditUserCommentInput input) {
       input = EditUserCommentInput.builder()
               .commentId(commentId)
               .content(input.getContent())
               .firstName(input.getFirstName())
               .lastName(input.getLastName())
               .roomNo(input.getRoomNo())
               .build();

       return new ResponseEntity<>(systemService.editUserComment(input), HttpStatus.OK);
    }

    @DeleteMapping("system/comment/{commentId}")
    public ResponseEntity<DeleteRoomCommentOutput> deleteUserComment(@PathVariable String commentId) {
        DeleteRoomCommentInput input = DeleteRoomCommentInput.builder()
                .commentId(commentId).build();
        return new ResponseEntity<>(systemService.deleteRoomComment(input), HttpStatus.OK);
    }
}
