package com.example.taskmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.UserRequestDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.UserNotFound;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.repository.UserRepository;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    public UserResponseDto createUser(UserRequestDto dto, Long id){

            User user = userMapper.toEntity(dto);
            userRepository.save(user);

            return userMapper.toResponseDto(user);

    }

    public UserResponseDto showUser(Long userId){
        
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User with ID " + userId + " not found"));
        return userMapper.toResponseDto(user);
    }

    public List<UserResponseDto> showAllUsers(){

        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto editUser(UserRequestDto dto, Long userId){ 
        
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User with ID " + userId + " not found"));

        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(dto.getPassword()));
        userRepository.save(user);

        return userMapper.toResponseDto(user);
    
    }

    public void deleteUser(Long userId){
        
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User with ID " + userId + " not found"));
        userRepository.delete(user);

    }
}
