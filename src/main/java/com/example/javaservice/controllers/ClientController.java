package com.example.javaservice.controllers;

import com.example.javaservice.model.dto.ClientDto;
import com.example.javaservice.model.entity.Client;
import com.example.javaservice.model.mapper.ClientMapper;
import com.example.javaservice.services.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Clients", description = "")
@RequestMapping(path = "api/clients/sing_up")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public void createNewClient ( @RequestBody ClientDto clientDto ) {
        clientService.createClient(ClientMapper.INSTANCE.toEntity(clientDto));

    }
    @GetMapping(value = "{clientId}")
    public ClientDto getClientById ( @PathVariable(name = "clientId") Long id ) {
        Client client = clientService.getClientById(id);
        return ClientMapper.INSTANCE.toDTO(client);
    }

    @GetMapping
    public List<ClientDto> getAllClients ( ) {
        return ClientMapper.INSTANCE.toListDto(clientService.getAllClient());
    }

    @DeleteMapping(value = "{clientId}")
    public void deleteClientById ( @PathVariable(name = "clientId") Long id ) {
        clientService.deleteClientById(id);
    }

    @PutMapping(value = "{clientId}")
    public void changeClientData ( @RequestBody ClientDto clientDto, @PathVariable(name = "clientId") Long id ) {
        clientService.changeClientData(ClientMapper.INSTANCE.toEntity(clientDto), id);
    }
}
