package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.contracts.SystemService;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteRoomCommentOutput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentInput;
import com.tinqinacademy.comments.api.operations.editusercomment.EditUserCommentOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Override
    public EditUserCommentOutput editUserComment(EditUserCommentInput input) {

        log.info("Start editUserComment {}", input);

        EditUserCommentOutput output  = EditUserCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End editUserComment {}", output);

        return output;
    }

    @Override
    public DeleteRoomCommentOutput deleteRoomComment(DeleteRoomCommentInput input) {
        log.info("Start deleteRoomComment {}", input);

        DeleteRoomCommentOutput output = DeleteRoomCommentOutput.builder().build();

        log.info("End deleteRoomComment {}", output);

        return output;
    }
}
