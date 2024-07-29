package com.tinqinacademy.comments.persistence.repositories;

import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findAllByRoomId(UUID roomId);
}
