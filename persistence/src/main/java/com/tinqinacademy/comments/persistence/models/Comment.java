package com.tinqinacademy.comments.persistence.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @CreationTimestamp
    private LocalDateTime publishDate;

    @UpdateTimestamp
    private LocalDateTime lastEditedDate;

    private String lastEditedBy;

    @Column(nullable = false)
    private UUID roomId;
}
