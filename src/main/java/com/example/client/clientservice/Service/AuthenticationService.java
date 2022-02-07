package com.example.client.clientservice.Service;


import com.example.client.clientservice.DTO.AuthenticationDTO;
import com.example.client.clientservice.DTO.AuthenticationTokenDTO;

public interface AuthenticationService {
    AuthenticationTokenDTO authenticate(AuthenticationDTO authenticationDto);
}
