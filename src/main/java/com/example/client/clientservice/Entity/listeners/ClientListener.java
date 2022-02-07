package com.example.client.clientservice.Entity.listeners;

import com.example.client.clientservice.Entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class ClientListener {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    public void encodePasswordBeforeInsertion(Client client) {
        client.setPassword(
                passwordEncoder.encode(client.getPassword())
        );
    }
}
