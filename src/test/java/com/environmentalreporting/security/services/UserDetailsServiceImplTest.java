package com.environmentalreporting.security.services;

import com.environmentalreporting.models.User;
import com.environmentalreporting.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserRepository userRepository;

    private final User user = new User(1L, "mock", "mockMail", "mockPassword", 0L, "mock", new HashSet<>(),new HashSet<>());

    @Test
    public void test() {
        // given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        UserDetails expected = UserDetailsImpl.build(user);

        // when
        UserDetails result = userDetailsService.loadUserByUsername("mock");

        // then
        assertEquals(expected, result);
    }
}
