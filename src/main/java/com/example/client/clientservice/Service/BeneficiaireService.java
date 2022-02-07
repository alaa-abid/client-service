package com.example.client.clientservice.Service;

import com.example.client.clientservice.Entity.Beneficiaire;

import java.util.List;

public interface BeneficiaireService {
    public List<Beneficiaire> getBeneficiaires(String id);
    public void removeBeneficiaire(Integer id);


}
