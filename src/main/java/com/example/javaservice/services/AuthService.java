package com.example.javaservice.services;

import com.example.javaservice.model.entity.Client;
import com.example.javaservice.security.jwt.JwtRequest;
import com.example.javaservice.security.jwt.JwtResponse;
import com.example.javaservice.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private ClientService clientService;
    private final JwtTokenProvider jwtProvider;

    public JwtResponse login ( @NonNull JwtRequest authRequest ) throws AuthException {
        final Client client = clientService.getClientByEmail(authRequest.getEmail())
                .orElseThrow(( ) -> new AuthException("Client Not Found"));
        if (clientService.checkPass(authRequest.getPassword(), client.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(client);
            final String refreshToken = jwtProvider.generateRefreshToken(client);
            clientService.changeOrAddRefreshToken(client, client.getId(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public JwtResponse getAccessToken ( @NonNull String refreshToken ) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final Client client = clientService.getClientByEmail(email)
                    .orElseThrow(( ) -> new AuthException("Client not Found"));
            final String saveRefreshToken = client.getRefreshToken();
            if (saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(client);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh ( @NonNull String refreshToken ) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final Client client = clientService.getClientByEmail(email)
                    .orElseThrow(( ) -> new AuthException("Client Not found"));
            final String saveRefreshToken = client.getRefreshToken();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(client);
                final String newRefreshToken = jwtProvider.generateRefreshToken(client);
                clientService.changeOrAddRefreshToken(client, client.getId(), refreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT");
    }
//    public JwtAuthentication getAuthInfo() {
//        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//    }

}