package com.tinqinacademy.comments.persistence.repositories;

import com.tinqinacademy.comments.persistence.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
  List<Comment> findAllByRoomId(UUID roomId);
}
