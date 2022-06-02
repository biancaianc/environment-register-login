package com.environmentalreporting.controllers;

import com.environmentalreporting.models.ENews;
import com.environmentalreporting.models.News;
import com.environmentalreporting.payload.requests.NewsRequest;
import com.environmentalreporting.payload.responses.NewsResponse;
import com.environmentalreporting.repositories.NewsRepository;
import com.environmentalreporting.security.services.NewsService;
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

    @GetMapping("/")
    public ResponseEntity<List<NewsResponse>> getAllNews() {
        try {
            return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(newsService.getNews(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<News> createNews(@Valid @RequestBody NewsRequest news) {
        try {
            return new ResponseEntity<>(newsService.createNews(news), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<News> updateNews(@Valid @RequestBody News news) {
        try {
            return new ResponseEntity<>(newsService.updateNews(news), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long id) {
        try {
            System.out.println("Rerere");
            newsService.deleteNews(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

}
