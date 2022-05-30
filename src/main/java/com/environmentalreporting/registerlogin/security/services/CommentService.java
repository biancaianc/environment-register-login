package com.environmentalreporting.registerlogin.security.services;

import com.environmentalreporting.registerlogin.models.Comment;
import com.environmentalreporting.registerlogin.payload.requests.CommentRequest;
import com.environmentalreporting.registerlogin.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public Comment createComment(CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest.getUsername(), commentRequest.getLikes(), commentRequest.getText(), commentRequest.getDate(), commentRequest.getNews());
        this.commentRepository.save(comment);
        return comment;
    }
}
