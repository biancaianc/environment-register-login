package com.environmentalreporting.payload.requests;

import com.environmentalreporting.models.News;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentRequest {

    private String username;

    private int likes;

    private String text;

    private Date date;

    private News news;
}
