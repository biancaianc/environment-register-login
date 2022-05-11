package com.environmentalreporting.registerlogin.payload.responses;

import com.environmentalreporting.registerlogin.models.EReport;
import com.environmentalreporting.registerlogin.models.User;
import java.util.Date;

public class ReportResponse {
    private Long id;

    private String name;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EReport getType() {
        return type;
    }

    public void setType(EReport type) {
        this.type = type;
    }

    private String city;

    private String region;

    private Float latitude;

    private Float longitude;

    private User user;

    private boolean approved = false;

    private String description;

    private EReport type;

    public ReportResponse(Long id, String name, Date date, String city, String region, Float latitude, Float longitude, User user, boolean approved, String description, String type) {
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
    }


}


