package com.example.client.clientservice.Service;

import com.example.client.clientservice.Entity.Compte;

public interface CompteService {
    public Compte verifySolde(Compte compte, Float amount);
}
