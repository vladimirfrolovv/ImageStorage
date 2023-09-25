package com.example.javaservice.model.mapper;

import com.example.javaservice.model.dto.ClientDto;
import com.example.javaservice.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto toDTO ( Client entity );

    Client toEntity ( ClientDto dto );

    List<ClientDto> toListDto ( List<Client> clients );

}