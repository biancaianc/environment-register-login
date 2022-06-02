package com.environmentalreporting.payload.responses;

import com.environmentalreporting.models.Comment;
import com.environmentalreporting.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private long id;

    private Date date;

    private String title;

    private String imagePath;

    private String shortDescription;

    private String content;

    private User user;

    private String type;

    private Set<Comment> comments;
}
