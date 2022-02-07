package com.example.client.clientservice.Utility;


import com.example.client.clientservice.Entity.Client;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentClient {

    public static Client get() {
        Client client = null;

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof Client) {
            client = (Client) principal;
        }

        return client;

    }

}
