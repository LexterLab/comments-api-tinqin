package com.tinqinacademy.comments.rest.config;

import com.tinqinacademy.comments.api.RouteExports;
import com.tinqinacademy.restexportprocessor.main.RestExportConfig;
import org.springframework.context.annotation.Configuration;

@RestExportConfig(destination = RouteExports.CLIENT, clientName = "CommentClient")
@Configuration
public class CustomRestExportConfig {
}
