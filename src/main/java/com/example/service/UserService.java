package com.example.service;

import com.example.dto.request.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {
    void setPassword(ChangePasswordRequest request, Principal principal);
}
