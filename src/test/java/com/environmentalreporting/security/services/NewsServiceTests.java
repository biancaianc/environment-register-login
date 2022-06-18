package com.environmentalreporting.security.services;

import com.environmentalreporting.models.ENews;
import com.environmentalreporting.models.News;
import com.environmentalreporting.models.User;
import com.environmentalreporting.payload.requests.NewsRequest;
import com.environmentalreporting.payload.responses.NewsResponse;
import com.environmentalreporting.repositories.NewsRepository;
import com.environmentalreporting.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NewsServiceTests {

    @Autowired
    NewsService newsService;

    @MockBean
    NewsRepository newsRepository;

    @MockBean
    UserRepository userRepository;

    private final NewsRequest newsRequest = new NewsRequest(new Date(), "mock","mock","mock", "mock", "mock", ENews.POLUARE.name());
    private final User user = new User(1L, "mock", "mockMail", "mockPassword", 0L, "mock", new HashSet<>(),new HashSet<>());
    private final News news = new News(null, newsRequest.getDate(), "mock","mock","mock", "mock", user, ENews.POLUARE, null);
    private final NewsResponse newsResponse = new NewsResponse(1L, newsRequest.getDate(), "mock","mock","mock", "mock", user, ENews.POLUARE.name(),null);

    @Test
    public void createNewsSuccess() throws Exception {
        // given
        when(newsRepository.save(any(News.class))).thenReturn(news);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // when
        News result = newsService.createNews(newsRequest);

        // then
        verify(newsRepository, times(1)).save(any());
        assertEquals(news, result);
    }

    @Test
    public void createNewsUserNotFound() throws Exception {
        // given
        when(newsRepository.save(any(News.class))).thenReturn(news);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // when
        assertThrows(Exception.class, () -> newsService.createNews(newsRequest) );

        // then
        verify(newsRepository, times(0)).save(any());
    }

    @Test
    public void getNewsById() {
        // given
       when(newsRepository.findById(any())).thenReturn(Optional.of(news));

        // when
        News result = newsService.getNews(1L);

        // then
        verify(newsRepository, times(1)).findById(any());
        assertEquals(news, result);
    }

    @Test
    public void getAllNews() {
        // given
        List<NewsResponse> newsResponseList = new ArrayList<>();
        List<News> newsList = new ArrayList<>();
        news.setId(1L);
        newsList.add(news);
        newsResponse.setId(1L);
        newsResponseList.add(newsResponse);
        when(newsRepository.findAll(any(Sort.class))).thenReturn(newsList);

        // when
        List<NewsResponse> result = newsService.getAllNews();

        // then
        verify(newsRepository, times(1)).findAll(any(Sort.class));
        assertEquals(newsResponseList, result);
    }

    @Test
    public void testUpdateNews() {
        when(newsRepository.save(any())).thenReturn(news);
        assertEquals(news, newsService.updateNews(news));
    }
    @Test
    public void tesDeleteNews() {
        newsService.deleteNews(1L);
        verify(newsRepository, times(1)).deleteById(any());
    }
}
