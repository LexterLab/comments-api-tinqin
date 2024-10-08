package com.tinqinacademy.comments.api.error;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Error {
    private String message;
    private String field;
}
