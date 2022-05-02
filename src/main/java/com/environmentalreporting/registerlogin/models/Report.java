package com.environmentalreporting.registerlogin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "reports", schema = "env_schema")
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreationTimestamp
    private Timestamp date;

    private String city;

    private String region;

    private Long latitude;

    private Long longitude;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean approved = false;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EReport type;

    public Report(String name, Timestamp date, String city, String region, Long latitude, Long longitude, User user, boolean approved, String description, String type) {
        this.name = name;
        this.date = date;
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.approved = approved;
        this.description = description;
        this.type = EReport.valueOf(type);
    }

    public Report() {
    }
}
