package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SystemServiceImplTest {

    @InjectMocks
    private SystemServiceImpl systemService;



    @Test
    void shouldReturnCommentIdWhenEditingUserComment() {
        EditUserCommentInput input = EditUserCommentInput.builder()
                .commentId("2")
                .firstName("George")
                .lastName("Russell")
                .roomNo("201A")
                .content("tHIS ROOM WAS SICK BRO")
                .build();

        EditUserCommentOutput expectedOutput = EditUserCommentOutput.builder()
                .id("2")
                .build();

        EditUserCommentOutput output = systemService.editUserComment(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }

    @Test
    void shouldReturnEmptyCommentWhenDeletingUserComment() {
        DeleteRoomCommentInput input = DeleteRoomCommentInput.builder()
                .commentId("2")
                .build();

        DeleteRoomCommentOutput expectedOutput = DeleteRoomCommentOutput.builder()
                .build();

        DeleteRoomCommentOutput output = systemService.deleteRoomComment(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }
}