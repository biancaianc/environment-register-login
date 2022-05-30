package com.environmentalreporting.registerlogin.security.services;

import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.NewsRequest;
import com.environmentalreporting.registerlogin.payload.responses.NewsResponse;
import com.environmentalreporting.registerlogin.repositories.NewsRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserRepository userRepository;

    public News createNews(NewsRequest news) throws Exception {
        Optional<User> user = userRepository.findByUsername(news.getUser());
        if (user.isPresent()) {
            User u = user.get();
            News entity = new News(news.getDate(), news.getTitle(), news.getImagePath(), news.getShortDescription(), news.getContent(), u, news.getType());
            newsRepository.save(entity);
            return entity;
        } else throw new Exception("Invalid user");

    }

    public News getNews(Long id) {
        News news = null;
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            news = byId.get();
        }
        return news;
    }

    public List<NewsResponse> getAllNews() {
        List<News> news = new ArrayList<>();
        List<NewsResponse> newsResponses = new ArrayList<>();
        newsRepository.findAll().forEach(news::add);
        news.forEach(x -> newsResponses.add(new NewsResponse(x.getId(), x.getDate(), x.getTitle(), x.getImagePath(), x.getShortDescription(), x.getContent(), x.getUser(), x.getType().name(), x.getComments())));
        return newsResponses;
    }

    public void updateNews(News news) {
        newsRepository.save(news);
    }
}
