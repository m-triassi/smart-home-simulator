package com.soen343.smarthomesimulator.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

import com.soen343.smarthomesimulator.models.User;

public class UserDetailsModel implements UserDetails{

    private String email;
    private String password;
    private String name;
    private Long id;
    private String role;

    public UserDetailsModel(String email){
        //maybe add variable userRepo autowired to UserRepository and call findByEmail(email)
    }

    public UserDetailsModel(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.id = user.getId();
        this.role = user.getRole();
    }

    public UserDetailsModel(){
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
        return email;
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
