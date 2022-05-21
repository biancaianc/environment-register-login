package com.environmentalreporting.registerlogin.payload.responses;

import com.environmentalreporting.registerlogin.models.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;

    private String title;
    private String description;
    private Date date;
    private Integer maxNumberOfParticipants;
    private String location;
    private String telephone;
    private Integer duration;
    private String imageName;
    private Set<User> users;
}
