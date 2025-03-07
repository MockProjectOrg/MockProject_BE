package org.example.bookingbe.service.UserDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.bookingbe.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserPriciple implements UserDetails {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String userName;
    @JsonIgnore
    private String password;

    public UserPriciple(){}
    public UserPriciple(User user) {}

    public Collection<? extends GrantedAuthority> authorities;
    public UserPriciple(Long id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }
    public static UserPriciple create(User user) {
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
        return new UserPriciple(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
}
