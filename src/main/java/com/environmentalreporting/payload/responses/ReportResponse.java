package com.environmentalreporting.payload.responses;

import com.environmentalreporting.models.EReport;
import com.environmentalreporting.models.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportResponse {
    private Long id;

    private String name;

    private Date date;

    private String city;

    private String region;

    private Float latitude;

    private Float longitude;

    private User user;

    private boolean approved = false;

    private String description;

    private EReport type;

    private String imagePath;

    private Integer reactions;

    public ReportResponse(Long id, String name, Date date, String city, String region, Float latitude, Float longitude, User user, boolean approved, String description, String type, String imagePath, Integer reactions) {
        this.id=id;
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

}


