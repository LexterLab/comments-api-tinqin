package com.tinqinacademy.comments.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tinqinacademy.comments"})
@EntityScan(basePackages = {"com.tinqinacademy.comments.persistence.models"})
@EnableJpaRepositories(basePackages = {"com.tinqinacademy.comments.persistence.repositories"})
@EnableFeignClients(basePackages = {"com.tinqinacademy.comments"})
public class CommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }
}
