package com.environmentalreporting.payload.requests;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class NewsRequest {
    private Date date;

    private String title;

    private String imagePath;

    private String shortDescription;

    private String content;

    private String user;

    private String type;
}
