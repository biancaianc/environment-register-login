package com.environmentalreporting.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "event", schema = "env_schema")
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date date;
    private Integer maxNumberOfParticipants;
    private String location;
    private String telephone;
    private Integer duration;
    private String imagePath;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JsonIgnore
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(
            name = "user_event",
            joinColumns ={ @JoinColumn(name = "event_id")},
            inverseJoinColumns ={ @JoinColumn(name = "user_id")})
    private Set<User> users;

    public Event() {

    }
    public Event(String title, String description, Date date, Integer maxNumberOfParticipants, String location, String telephone, Integer duration, String imagePath, Set<User> users)
    {
        this.title = title;
        this.description = description;
        this.date = date;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.location = location;
        this.telephone = telephone;
        this.duration = duration;
        this.imagePath = imagePath;
        this.users = users;
    }

}
