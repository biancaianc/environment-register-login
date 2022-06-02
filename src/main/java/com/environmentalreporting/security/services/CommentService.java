package com.environmentalreporting.security.services;

import com.environmentalreporting.models.Comment;
import com.environmentalreporting.payload.requests.CommentRequest;
import com.environmentalreporting.repositories.CommentRepository;
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

    public Comment getComment(Long id) {
        return  commentRepository.findById(id).get();
    }
}
