package com.example.service.impl;

import com.example.config.google.CustomOAuth2User;
import com.example.config.security.JwtService;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.response.AuthResponse;
import com.example.entity.Provider;
import com.example.entity.Role;
import com.example.entity.Token;
import com.example.entity.User;
import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void setPassword(ChangePasswordRequest request, Principal principal) {

    }

    public AuthResponse processOAuthPostLogin(CustomOAuth2User oAuth2User, HttpServletResponse response) throws IOException {
        var existUser = userRepository.findByUsername(oAuth2User.getEmail());
        System.out.println(oAuth2User.getAttributes());
        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(oAuth2User.getEmail());
            newUser.setProvider(Provider.GOOGLE);
            newUser.setRole(Role.USER);
            newUser.setFirstName(oAuth2User.getAttribute("given_name"));
            newUser.setLastName(oAuth2User.getAttribute("family_name"));
            var savedUser = userRepository.save(newUser);
            System.out.println("Created new user: " + oAuth2User.getEmail());

            var jwtToken = jwtService.generateToken(newUser);
            var refreshToken = jwtService.generateRefreshToken(newUser);

            var token = Token.builder()
                    .user(newUser)
                    .token(jwtToken)
                    .expired(false)
                    .revoked(false)
                    .build();
            tokenRepository.save(token);
//            Map<String, Object> body = new HashMap<>();
//            body.put("accessToken", jwtToken);
//            body.put("refreshToken", refreshToken);
//            objectMapper.writeValue(response.getOutputStream(), body);
            return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
        }
        return null;


    }
}
