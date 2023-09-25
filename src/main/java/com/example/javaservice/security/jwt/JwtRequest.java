package com.example.javaservice.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    String email;
    String password;

}
