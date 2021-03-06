package com.environmentalreporting.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "report", schema = "env_schema")
@AllArgsConstructor
@EqualsAndHashCode
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date date;

    private String city;

    private String region;

    private Float latitude;

    private Float longitude;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    private boolean approved = false;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EReport type;

    private String imagePath;

    private Integer reactions;

    public Report(String name, String city, String region, Float latitude, Float longitude, User user, boolean approved, String description, String type, String imagePath, Integer reactions) {
        this.name = name;
        this.date = new Date();
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.approved = approved;
        this.description = description;
        this.type = EReport.valueOf(type);
        this.imagePath = imagePath;
        this.reactions = reactions;
    }

    public Report() {
    }
}
