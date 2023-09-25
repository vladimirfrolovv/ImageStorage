package com.example.javaservice.services;

import com.example.javaservice.model.entity.Client;
import com.example.javaservice.repisotory.ClientRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void createClient ( Client client ) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    @Override
    public Client getClientById ( Long id ) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public List<Client> getAllClient ( ) {
        return clientRepository.findAll();
    }

    @Override
    public boolean deleteClientById ( Long id ) {
        if (clientRepository.existsById(id)) {
            clientRepository.delete(clientRepository.getReferenceById(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean changeClientData ( Client client, Long id ) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Client> getClientByEmail ( String email ) {
        return clientRepository.findClientByEmail(email);
    }

    @Override
    public void changeOrAddRefreshToken ( Client client, Long id, String refreshToken ) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            client.setRefreshToken(refreshToken);
            clientRepository.save(client);
        }
    }

    @Override
    public String getRefreshToken ( String email ) {
        Optional<Client> client = clientRepository.findClientByEmail(email);
        return client.map(Client::getRefreshToken).orElse(null);
    }

    @Override
    public boolean checkPass ( String password, String passwordEncode ) {
        return passwordEncoder.matches(password, passwordEncode);
    }

}
