package com.tinqinacademy.comments.core.configs;

import com.tinqinacademy.comments.core.converters.impl.CommentToRoomCommentOutput;
import com.tinqinacademy.comments.core.converters.impl.LeaveRoomCommentInputToComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
        private final CommentToRoomCommentOutput commentToRoomCommentOutput;
        private final LeaveRoomCommentInputToComment leaveRoomCommentInputToComment;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(commentToRoomCommentOutput);
        registry.addConverter(leaveRoomCommentInputToComment);
    }
}