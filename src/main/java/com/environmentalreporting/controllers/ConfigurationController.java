package com.environmentalreporting.controllers;

import com.environmentalreporting.models.ENews;
import com.environmentalreporting.models.EReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/config")
@Slf4j
public class ConfigurationController {

    @GetMapping("/newsTypes")
    public ResponseEntity<List<String>> getNewsTypes() {
        List<String> newsTypes;
        try {
            newsTypes = Arrays.stream(ENews.values()).map(x -> x.name()).collect(Collectors.toList());
            return new ResponseEntity<>(newsTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reportTypes")
    public ResponseEntity<List<String>> getReportTypes() {
        try {
            return new ResponseEntity<>(Arrays.stream(EReport.values()).map(x -> x.name()).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
