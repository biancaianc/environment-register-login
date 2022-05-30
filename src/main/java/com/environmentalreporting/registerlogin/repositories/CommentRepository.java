package com.environmentalreporting.registerlogin.repositories;

import com.environmentalreporting.registerlogin.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
