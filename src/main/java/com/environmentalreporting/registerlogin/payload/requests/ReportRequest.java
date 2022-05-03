package com.environmentalreporting.registerlogin.payload.requests;

import com.environmentalreporting.registerlogin.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ReportRequest {
    private String name;

    private String city;

    private String region;

    private Float latitude;

    private Float longitude;

    private User user;

    private boolean approved = false;

    private String description;

    private String type;

    public ReportRequest(String name, String city, String region, Float latitude, Float longitude, User user, boolean approved, String description, String type) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.approved = approved;
        this.description = description;
        this.type = type;
    }

}
