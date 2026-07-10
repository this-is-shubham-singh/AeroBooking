package com.micro.user_service.service.Impl;

import com.micro.enums.UserRole;
import com.micro.payload.dto.UserDto;
import com.micro.payload.response.AuthResponse;
import com.micro.user_service.config.JwtProvider;
import com.micro.user_service.model.User;
import com.micro.user_service.repository.UserRepository;
import com.micro.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtProvider jwtProvider;

        @Override
        public AuthResponse signup(UserDto userDto) {
                if (userRepository.existsByEmail(userDto.getEmail())) {
                        throw new RuntimeException("Email already exists: " + userDto.getEmail());
                }

                if (userDto.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
                        throw new RuntimeException("cannot create admin id");
                }

                User user = User.builder()
                                .fullName(userDto.getFullName())
                                .email(userDto.getEmail())
                                .phone(userDto.getPhone())
                                .password(passwordEncoder.encode(userDto.getPassword()))
                                .role(userDto.getRole())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                User savedUser = userRepository.save(user);

                UserDto savedUserDto = UserDto.builder()
                                .id(savedUser.getId())
                                .email(savedUser.getEmail())
                                .fullName(savedUser.getFullName())
                                .phone(savedUser.getPhone())
                                .role(savedUser.getRole())
                                .build();

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                savedUser.getEmail(),
                                savedUser.getPassword());

                String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

                AuthResponse authResponse = new AuthResponse();
                authResponse.setJwt(jwt);
                authResponse.setMessage("Registered Successfully!");
                authResponse.setTitle("Welcome " + savedUser.getFullName());
                authResponse.setUser(savedUserDto);

                return authResponse;
        }

        @Override
        public AuthResponse login(String email, String password) {
                // 1. Email ke base par database se user fetch karna (Nahi milne par exception
                // throw karna)
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

                // 2. Client se aaye raw password ko database ke encrypted password se compare
                // karna
                if (!passwordEncoder.matches(password, user.getPassword())) {
                        throw new RuntimeException("Invalid email or password");
                }

                // 3. User ka login time update karke database me save karna
                user.setLastLogin(LocalDateTime.now());
                User savedUser = userRepository.save(user);

                // 4. User role ko Spring Security ke GrantedAuthority standard objects me map
                // karna
                List<GrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority(savedUser.getRole().name()));

                // 5. Spring Security ka authenticated object (identity context) manually create
                // karna
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                savedUser.getEmail(),
                                savedUser.getPassword(),
                                authorities);

                // 6. Security context aur user ID ke sath custom JWT Token generate karna
                String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

                // 7. Returned user details ko UserDto response DTO me map karna
                UserDto userDto = UserDto.builder()
                                .id(savedUser.getId())
                                .email(savedUser.getEmail())
                                .fullName(savedUser.getFullName())
                                .phone(savedUser.getPhone())
                                .role(savedUser.getRole())
                                .lastLogin(savedUser.getLastLogin())
                                .build();

                // 8. Token, message aur user DTO ko final response object me pack karna
                AuthResponse authResponse = new AuthResponse();
                authResponse.setJwt(jwt);
                authResponse.setMessage("Login Successful!");
                authResponse.setTitle("Welcome Back " + savedUser.getFullName());
                authResponse.setUser(userDto);

                return authResponse;
        }
}
