package com.example.javaservice.model.dto;

import lombok.*;

@Getter
@Setter
public class ClientDto {
    Long id;
    String login;
    String email;
    String password;
}
