package com.micro.user_service.service;

import com.micro.payload.dto.UserDto;
import com.micro.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password);

    AuthResponse signup(UserDto userDto);
}