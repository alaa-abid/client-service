package com.example.client.clientservice.Service.impl;


import com.example.client.clientservice.Controller.Converter.ClientConverter;
import com.example.client.clientservice.DTO.AuthenticationDTO;
import com.example.client.clientservice.DTO.AuthenticationTokenDTO;
import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Exception.InvalidFieldException;
import com.example.client.clientservice.Service.AuthenticationService;
import com.example.client.clientservice.Utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final JwtUtility jwtUtil;

    private final ClientConverter clientConverter;


    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtUtility jwtUtil, ClientConverter clientConverter) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.clientConverter = clientConverter;
    }

    @Override
    public AuthenticationTokenDTO authenticate(AuthenticationDTO authenticationDto) {
        Authentication authResult;

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationDto.getEmail(),
                    authenticationDto.getPassword()
            );

            authResult = authenticationManager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new InvalidFieldException();
        }

        final Client client = (Client) authResult.getPrincipal();

        String token = jwtUtil.generateToken(client);

        return AuthenticationTokenDTO.builder()
                .token(token)
                .id(client.getId())
                .build();
    }



}
