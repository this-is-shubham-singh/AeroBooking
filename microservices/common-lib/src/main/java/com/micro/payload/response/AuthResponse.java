package com.micro.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.micro.payload.dto.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    String jwt;
    String message;
    String title;
    UserDto user;
}
