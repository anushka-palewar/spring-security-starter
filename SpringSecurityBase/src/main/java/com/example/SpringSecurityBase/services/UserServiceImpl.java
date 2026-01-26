package com.example.SpringSecurityBase.services;

import com.example.SpringSecurityBase.dtos.UserDto;
import com.example.SpringSecurityBase.entities.Provider;
import com.example.SpringSecurityBase.entities.User;
import com.example.SpringSecurityBase.exceptions.ResourceNotFoundException;
import com.example.SpringSecurityBase.helpers.UserHelper;
import com.example.SpringSecurityBase.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        // 1. Basic validation
        if(userDto.getEmail()==null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }

        // 2. Business rule validation
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists!");
        }

        // 3. Convert incoming DTO → Entity
        User user = modelMapper.map(userDto, User.class);

        // 4. Set default value if not provided
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

        // 5. Save to database
        User savedUser = userRepository.save(user);

        // 6. Convert saved entity back → DTO (for response)
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        User user = userRepository.findByEmail(email.trim())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUset(UserDto userDto, String userId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        UUID uid= UserHelper.parseUUID(userId);
        User user=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("User Not found with given id"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
