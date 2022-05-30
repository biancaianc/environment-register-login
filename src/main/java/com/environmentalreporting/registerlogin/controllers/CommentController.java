package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.models.Comment;
import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.payload.requests.CommentRequest;
import com.environmentalreporting.registerlogin.payload.requests.NewsRequest;
import com.environmentalreporting.registerlogin.security.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        System.out.println("HEREE");
        try {
            return new ResponseEntity<>(commentService.createComment(commentRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
