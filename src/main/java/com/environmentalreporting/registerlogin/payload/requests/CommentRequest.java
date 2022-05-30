package com.environmentalreporting.registerlogin.payload.requests;

import com.environmentalreporting.registerlogin.models.News;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
