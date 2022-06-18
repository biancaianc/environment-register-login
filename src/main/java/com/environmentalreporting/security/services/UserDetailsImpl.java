package com.environmentalreporting.security.services;

import java.util.Collection;
import java.util.Objects;

import com.environmentalreporting.models.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String role;
    private Long id;
    private String username;
    private String email;

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    private Long points;
    @JsonIgnore
    private String password;
    public UserDetailsImpl(Long id, String username, String email, String password,
                           String authorities, Long points ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = authorities;
        this.points=points;
    }
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        user.getPoints());
    }

    public String getRole() {
        return role;
    }
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
