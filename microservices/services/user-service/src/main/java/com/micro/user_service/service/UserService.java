package com.micro.user_service.service;

import com.micro.payload.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();
}
