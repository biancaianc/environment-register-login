package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.models.EReport;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.repositories.ReportRepository;
import com.environmentalreporting.registerlogin.security.jwt.JwtUtils;
import com.environmentalreporting.registerlogin.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports;
        try {
            reports = reportRepository.findAllBy();
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reportTypes")
    public ResponseEntity<List<String>> getReportTypes() {
    List<String> reportTypes = null;
        try {
            reportTypes = Arrays.stream(EReport.values()).map(x -> x.name()).collect(Collectors.toList());
            return new ResponseEntity<>(reportTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        try {
            Report _report = reportRepository
                    .save(new Report(report.getName(), report.getDate(), report.getCity(), report.getRegion(), report.getLatitude(), report.getLongitude(), report.getUser(), report.isApproved(), report.getDescription(), report.getType().name()));
            return new ResponseEntity<>(_report, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
