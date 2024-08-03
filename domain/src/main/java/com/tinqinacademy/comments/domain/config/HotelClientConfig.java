package com.tinqinacademy.comments.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.restexport.HotelClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelClientConfig {

    @Bean
    public HotelClient getClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(HotelClient.class, "http://localhost:8080/api/v1");
    }
}
