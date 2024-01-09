package com.example.service;

import com.example.config.google.CustomOAuth2User;
import com.example.dto.request.AuthRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthResponse;
import com.example.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    /**login*/
    AuthResponse authenticate(AuthRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException;
     void saveUserToken(User user, String jwtToken);
//    void processOAuthPostLogin(CustomOAuth2User oAuth2User, HttpServletResponse response) throws IOException;
//    AuthResponse authWithGoogle();
}
