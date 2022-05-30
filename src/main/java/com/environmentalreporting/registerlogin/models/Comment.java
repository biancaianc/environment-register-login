package com.environmentalreporting.registerlogin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "comment", schema = "env_schema")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int likes;

    private String text;

    private Date date;

    @ManyToOne
    @JsonBackReference
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    public Comment(String username, int likes, String text, Date date, News news) {
        this.username = username;
        this.likes = likes;
        this.text = text;
        this.date = date;
        this.news = news;
    }
}
