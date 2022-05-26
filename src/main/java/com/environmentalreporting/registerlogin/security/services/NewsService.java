package com.environmentalreporting.registerlogin.security.services;

import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.NewsRequest;
import com.environmentalreporting.registerlogin.repositories.NewsRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }
        else throw new Exception("Invalid user");

    }

    public News getNews(Long id) {
        News news = null;
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            news = byId.get();
        }
        return news;
    }
}
