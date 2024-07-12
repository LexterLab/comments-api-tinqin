package com.tinqinacademy.comments.api.operations.error;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorDetails {
    private List<Error> errors;
}
