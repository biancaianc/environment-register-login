package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.registerlogin.models.EReport;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.ReportRequest;
import com.environmentalreporting.registerlogin.payload.responses.ReportResponse;
import com.environmentalreporting.registerlogin.repositories.ReportRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
import com.environmentalreporting.registerlogin.security.jwt.JwtUtils;
import com.environmentalreporting.registerlogin.security.services.ReportService;
import com.zaxxer.hikari.util.FastList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportService reportService;

    @GetMapping("/reports")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<Report> reports = new ArrayList<>();
        List<ReportResponse> reportResponses = new ArrayList<>();
        try {
            reportRepository.findAll().forEach(reports::add);
            reports.forEach(x -> reportResponses.add(new ReportResponse(x.getId(), x.getName(), x.getDate(), x.getCity(), x.getRegion(),
                    x.getLatitude(), x.getLongitude(), x.getUser(), x.isApproved(), x.getDescription(), x.getType().name(), x.getImagePath())));
            return new ResponseEntity<>(reportResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reportTypes")
    public ResponseEntity<List<String>> getReportTypes() {
        List<String> reportTypes;
        try {
            reportTypes = Arrays.stream(EReport.values()).map(x -> x.name()).collect(Collectors.toList());
            return new ResponseEntity<>(reportTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportRequest report) {
        try {
            return new ResponseEntity<>(reportService.createReport(report),HttpStatus.CREATED);
        } catch (AlreadyReportedInThatArea alreadyReportedInThatArea) {
            System.out.println(alreadyReportedInThatArea.getMessage());
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/approveReport/{id}")
    public ResponseEntity<String> updateReport(@PathVariable("id") long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            reportService.approveReport(report.get());
            return new ResponseEntity<>("approved", HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deleteReport/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") long id) {
        try {
            reportService.deleteReport(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<List<ReportResponse>> getReportsByUserId(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Optional<List<Report>> reporterData = reportRepository.findByUser(user.get());
            if (reporterData.isPresent()) {
                List<ReportResponse> reportResponses = new ArrayList<>();
                reporterData.get().forEach(x -> reportResponses.add(new ReportResponse(x.getId(), x.getName(), x.getDate(), x.getCity(), x.getRegion(),
                        x.getLatitude(), x.getLongitude(), x.getUser(), x.isApproved(), x.getDescription(), x.getType().name(), x.getImagePath())));
                return new ResponseEntity<>(reportResponses, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
