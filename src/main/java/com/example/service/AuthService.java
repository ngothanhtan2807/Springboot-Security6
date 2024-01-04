package com.example.service;

import com.example.dto.request.AuthRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    /**login*/
    AuthResponse authenticate(AuthRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException;
}
