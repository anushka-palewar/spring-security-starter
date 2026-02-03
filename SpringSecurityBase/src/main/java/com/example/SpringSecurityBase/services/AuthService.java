package com.example.SpringSecurityBase.services;

import com.example.SpringSecurityBase.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);
}
