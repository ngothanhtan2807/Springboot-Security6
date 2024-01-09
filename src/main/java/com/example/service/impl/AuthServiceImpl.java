package com.example.service.impl;

import com.example.config.google.CustomOAuth2User;
import com.example.config.security.JwtService;
import com.example.dto.request.AuthRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthResponse;
import com.example.entity.Provider;
import com.example.entity.Role;
import com.example.entity.Token;
import com.example.entity.User;
import com.example.helper.SecurityContextHelper;
import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHelper helper;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .provider(Provider.LOCAL)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserToken(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUserId(user.getId());
        validUserToken.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String header = request.getHeader("Authorization");
        System.out.println("header: " + header);
        final String refreshToken;
        final String username;
        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }
        refreshToken = header.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userRepository.findByUsername(username).get();
            if (jwtService.isTokenValid(refreshToken, (UserDetails) user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
//    public void processOAuthPostLogin(CustomOAuth2User oAuth2User, HttpServletResponse response) throws IOException {
//        var existUser = userRepository.findByUsername(oAuth2User.getEmail());
//        System.out.println(oAuth2User.getAttributes());
//        if (existUser.isEmpty()) {
//            User newUser = new User();
//            newUser.setUsername(oAuth2User.getEmail());
//            newUser.setProvider(Provider.GOOGLE);
//            newUser.setRole(Role.USER);
//            newUser.setFirstName(oAuth2User.getAttribute("given_name"));
//            newUser.setLastName(oAuth2User.getAttribute("family_name"));
//            var savedUser = userRepository.save(newUser);
//            System.out.println("Created new user: " + oAuth2User.getEmail());
//
//            var jwtToken = jwtService.generateToken(newUser);
//            var refreshToken = jwtService.generateRefreshToken(newUser);
//
//            saveUserToken(savedUser, jwtToken);
//            Map<String, Object> body = new HashMap<>();
//            body.put("accessToken", jwtToken);
//            body.put("refreshToken", refreshToken);
//            objectMapper.writeValue(response.getOutputStream(), body);
//
//        }
//
//    }
}
