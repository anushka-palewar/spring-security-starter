package com.example.SpringSecurityBase.security;

import com.example.SpringSecurityBase.entities.User;
import com.example.SpringSecurityBase.exceptions.ResourceNotFoundException;
import com.example.SpringSecurityBase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("Invalid email or Password!!"));
    }
}
