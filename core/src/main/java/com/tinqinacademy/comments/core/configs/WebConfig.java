package com.tinqinacademy.comments.core.configs;

import com.tinqinacademy.comments.core.converters.impl.CommentToRoomCommentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
        private final CommentToRoomCommentOutput commentToRoomCommentOutput;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(commentToRoomCommentOutput);
    }
}