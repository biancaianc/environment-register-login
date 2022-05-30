package com.environmentalreporting.registerlogin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Data
@Table(name = "news", schema = "env_schema")
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public News(Date date, String title, String imagePath, String shortDescription, String content, User user, String type) {
        this.date = date;
        this.title = title;
        this.imagePath = imagePath;
        this.shortDescription = shortDescription;
        this.content = content;
        this.user = user;
        this.type = ENews.valueOf(type);;
    }

    private Date date;

    private String title;

    private String imagePath;

    private String shortDescription;

    private String content;

    @ManyToOne
    @JsonBackReference
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ENews type;

    @OneToMany(mappedBy="news")
    private Set<Comment> comments;
}
