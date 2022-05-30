package com.environmentalreporting.registerlogin.payload.requests;

import com.environmentalreporting.registerlogin.models.Comment;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class NewsRequest {
    private Date date;

    private String title;

    private String imagePath;

    private String shortDescription;

    private String content;

    private String user;

    private String type;


    public NewsRequest(Date date, String title, String imagePath, String shortDescription, String content, String user, String type) {
        this.date = date;
        this.title = title;
        this.imagePath = imagePath;
        this.shortDescription = shortDescription;
        this.content = content;
        this.user = user;
        this.type = type;
      //  this.comments = comments;
    }
}
