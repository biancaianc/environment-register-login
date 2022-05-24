package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.registerlogin.models.ENews;
import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.payload.requests.NewsRequest;
import com.environmentalreporting.registerlogin.payload.requests.ReportRequest;
import com.environmentalreporting.registerlogin.payload.responses.ReportResponse;
import com.environmentalreporting.registerlogin.security.services.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@Slf4j
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/newsTypes")
    public ResponseEntity<List<String>> getNewsTypes() {
        List<String> newsTypes;
        try {
            newsTypes = Arrays.stream(ENews.values()).map(x -> x.name()).collect(Collectors.toList());
            return new ResponseEntity<>(newsTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @GetMapping("/reports")
//    public ResponseEntity<List<ReportResponse>> getAllReports() {
//        List<Report> reports = new ArrayList<>();
//        List<ReportResponse> reportResponses = new ArrayList<>();
//        try {
//            reportRepository.findAll().forEach(reports::add);
//            reports.forEach(x -> reportResponses.add(new ReportResponse(x.getId(), x.getName(), x.getDate(), x.getCity(), x.getRegion(),
//                    x.getLatitude(), x.getLongitude(), x.getUser(), x.isApproved(), x.getDescription(), x.getType().name(), x.getImagePath())));
//            return new ResponseEntity<>(reportResponses, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/")
    public ResponseEntity<News> createNews(@Valid @RequestBody NewsRequest news) {
        try {
            return new ResponseEntity<>(newsService.createNews(news),HttpStatus.CREATED);
//        } catch (AlreadyReportedInThatArea alreadyReportedInThatArea) {
//            System.out.println(alreadyReportedInThatArea.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
