package com.example.service;

import com.example.config.google.CustomOAuth2User;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    void setPassword(ChangePasswordRequest request, Principal principal);
    AuthResponse processOAuthPostLogin(CustomOAuth2User oAuth2User, HttpServletResponse response) throws IOException;
}
