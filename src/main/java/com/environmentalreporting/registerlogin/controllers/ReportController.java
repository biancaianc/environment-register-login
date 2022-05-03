package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.models.EReport;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.ReportRequest;
import com.environmentalreporting.registerlogin.repositories.ReportRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
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

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = new ArrayList<>();
        try {
            reportRepository.findAll().forEach(reports::add);
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
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportRequest report) {
        try {
            Report entity = new Report(report.getName(), report.getCity(), report.getRegion(), report.getLatitude(), report.getLongitude(), report.getUser(), report.isApproved(), report.getDescription(), report.getType());
            reportRepository.save(entity);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<List<Report>> getReportsByUserId(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            Optional<List<Report>> reporterData = reportRepository.findByUser(user.get());
            if (reporterData.isPresent()) {
                return new ResponseEntity<>(reporterData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
