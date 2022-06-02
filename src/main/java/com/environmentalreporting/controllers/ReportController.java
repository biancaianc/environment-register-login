package com.environmentalreporting.controllers;

import com.environmentalreporting.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.models.Report;
import com.environmentalreporting.payload.requests.ReportRequest;
import com.environmentalreporting.payload.responses.ReportResponse;
import com.environmentalreporting.repositories.ReportRepository;
import com.environmentalreporting.repositories.UserRepository;
import com.environmentalreporting.security.jwt.JwtUtils;
import com.environmentalreporting.security.services.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/report")
@Slf4j
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
        try {
            return new ResponseEntity<>(reportService.getReports(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportRequest report) {
        try {
            return new ResponseEntity<>(reportService.createReport(report), HttpStatus.CREATED);
        } catch (AlreadyReportedInThatArea alreadyReportedInThatArea) {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Report> updateReport(@Valid @RequestBody Report report) {
        try {
            log.info("Updating report with id " + report.getId());
            return new ResponseEntity<>(reportService.updateReport(report), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") long id) {
        try {
            reportService.deleteReport(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<List<ReportResponse>> getReportsByUserId(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(reportService.getReportsFromUser(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(reportService.getReport(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
