package com.example.client.clientservice.Controller.Converter;


import com.example.client.clientservice.DTO.ClientDTO;
import com.example.client.clientservice.Entity.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter implements AbstractConverter<Client, ClientDTO>{

    private final ModelMapper modelMapper;

    @Autowired
    public ClientConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        this.modelMapper = modelMapper;
    }

    @Override
    public Client convertToDM(ClientDTO clientDTO) {
        if (clientDTO == null)
            return null;

        return modelMapper.map(clientDTO, Client.class);
    }

    @Override
    public ClientDTO convertToDTO(Client client) {
        if (client == null)
            return null;
        return modelMapper.map(client, ClientDTO.class);
    }
}
