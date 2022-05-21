package com.environmentalreporting.registerlogin.payload.requests;

import com.environmentalreporting.registerlogin.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    private String title;
    private String description;
    private Date date;
    private Integer maxNumberOfParticipants;
    private String location;
    private String telephone;
    private Integer duration;
    private String imageName;
    private List<User> users;
}
