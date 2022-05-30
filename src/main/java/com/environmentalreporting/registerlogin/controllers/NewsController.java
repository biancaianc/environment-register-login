package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.models.ENews;
import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.payload.requests.NewsRequest;
import com.environmentalreporting.registerlogin.payload.responses.NewsResponse;
import com.environmentalreporting.registerlogin.repositories.NewsRepository;
import com.environmentalreporting.registerlogin.security.services.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
@Slf4j
public class NewsController {
    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

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

    @GetMapping("/")
    public ResponseEntity<List<NewsResponse>> getAllNews() {
        try {
            return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(newsService.getNews(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<News> createNews(@Valid @RequestBody NewsRequest news) {
        try {
            return new ResponseEntity<>(newsService.createNews(news), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
