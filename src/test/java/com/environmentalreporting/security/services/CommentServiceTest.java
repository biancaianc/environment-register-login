package com.environmentalreporting.security.services;

import com.environmentalreporting.models.Comment;
import com.environmentalreporting.payload.requests.CommentRequest;
import com.environmentalreporting.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @MockBean
    CommentRepository commentRepository;

    CommentRequest commentRequest = new CommentRequest();
    Comment comment = new Comment();
    @Test
    public void testCreateComment() {
        when(commentRepository.save(any())).thenReturn(comment);
        assertEquals(comment ,commentService.createComment(commentRequest));
    }
    @Test
    public void testGetComment() {
        when(commentRepository.findById(any())).thenReturn(Optional.ofNullable(comment));
        assertEquals(comment ,commentService.getComment(anyLong()));
    }

}
