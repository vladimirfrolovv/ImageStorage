package com.example.javaservice.services;

import com.example.javaservice.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void createClient ( Client client );

    Client getClientById ( Long id );

    List<Client> getAllClient ( );

    boolean deleteClientById ( Long id );

    boolean changeClientData ( Client client, Long id );

    Optional<Client> getClientByEmail ( String email );

    void changeOrAddRefreshToken ( Client client, Long id, String refreshToken );

    //TODO получать только по email
    String getRefreshToken ( String email );

    boolean checkPass ( String password, String passwordEncode );

}
