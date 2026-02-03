package com.example.SpringSecurityBase.services.impl;

import com.example.SpringSecurityBase.dtos.UserDto;
import com.example.SpringSecurityBase.services.AuthService;
import com.example.SpringSecurityBase.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        UserDto userDto1=userService.createUser(userDto);
        return userDto1;
    }
}
