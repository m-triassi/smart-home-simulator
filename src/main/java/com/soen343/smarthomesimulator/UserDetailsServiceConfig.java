package com.soen343.smarthomesimulator;

import java.util.Optional;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("email not found");

        return new User(user);
    }

}
