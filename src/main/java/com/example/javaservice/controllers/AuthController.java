package com.example.javaservice.controllers;

import com.example.javaservice.security.jwt.JwtRequest;
import com.example.javaservice.security.jwt.JwtResponse;
import com.example.javaservice.security.jwt.RefreshJwtRequest;
import com.example.javaservice.services.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign_in")
    public ResponseEntity<JwtResponse> login ( @RequestBody JwtRequest authRequest ) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken ( @RequestBody RefreshJwtRequest request ) {
        //TODO подумать, что делать с ошибкой
        try {
            final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken ( @RequestBody RefreshJwtRequest request ) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}