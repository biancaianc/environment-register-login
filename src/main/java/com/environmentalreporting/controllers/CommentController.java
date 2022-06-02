package com.environmentalreporting.controllers;

import com.environmentalreporting.models.Comment;
import com.environmentalreporting.payload.requests.CommentRequest;
import com.environmentalreporting.security.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        log.info("Create comment with text: " + commentRequest.getText());
        try {
            return new ResponseEntity<>(commentService.createComment(commentRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
        log.info("Get comment with id " + id);
        try {
            return new ResponseEntity<>(commentService.getComment(id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
