package com.example.client.clientservice.Service.impl;


import com.example.client.clientservice.Entity.Compte;
import com.example.client.clientservice.Exception.ClientException;
import com.example.client.clientservice.Repository.CompteRepository;
import com.example.client.clientservice.Service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Override
    public Compte verifySolde(Compte compte, Float amount) {

        Double current_amount = compte.getSolde();

        if(current_amount <= amount) throw new ClientException("Insufficient balance !");

                compte.setSolde(current_amount-amount);
                return compte;
    }
}
