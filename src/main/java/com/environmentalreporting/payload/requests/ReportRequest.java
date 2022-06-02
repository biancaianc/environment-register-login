package com.environmentalreporting.payload.requests;

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

    private String user;

    private boolean approved = false;

    private String description;

    private String type;

    private String imagePath;

    private Integer reactions;

    public ReportRequest(String name, String city, String region, Float latitude, Float longitude, String user, boolean approved, String description, String type, String imagePath, Integer reactions) {
        this.name = name;
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.approved = approved;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
        this.reactions = reactions;
    }

}
