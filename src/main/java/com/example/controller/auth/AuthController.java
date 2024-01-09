package com.example.controller.auth;

import com.example.dto.request.AuthRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthResponse;
import com.example.helper.SecurityContextHelper;
import com.example.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
private final AuthService authService;
private final SecurityContextHelper helper;
@PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(authService.register(request));
}
@PostMapping("/authenticated")
    public ResponseEntity<AuthResponse> authenticated(@RequestBody AuthRequest request){

    return ResponseEntity.ok(authService.authenticate(request));
}
@PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    authService.refreshToken(request, response);
}
@GetMapping("/return")
    public void login(){

}
}
