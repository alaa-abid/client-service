package com.example.client.clientservice.Service.impl;

import com.example.client.clientservice.Entity.Beneficiaire;
import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Exception.AlreadyExistsException;
import com.example.client.clientservice.Exception.ClientException;
import com.example.client.clientservice.Exception.NotFoundException;
import com.example.client.clientservice.Repository.BeneficiaireRepository;
import com.example.client.clientservice.Service.BeneficiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BeneficiaireServiceImpl implements BeneficiaireService {

    @Autowired
    BeneficiaireRepository rep;

    @Autowired
    ClientServiceImpl clientService;



    public List<Beneficiaire> getBeneficiairesByAPP(Integer id) {
       // Client client= clientService.getClientbyId(id);
        List<Beneficiaire> bens= new ArrayList<Beneficiaire>();
        bens = clientService.getBeneficiaires(id);
        if(bens.isEmpty())  throw new NotFoundException("Aucun compte trouvé");

        return bens;
    }

    @Override
    public List<Beneficiaire> getBeneficiaires(String id) {
        return null;
    }

    @Override
    public void removeBeneficiaire(Integer id) {

    }

  /*  public void removeBeneficiaire(Integer idClient, Integer id) {
        //vérifier l'existence du beneficiaire
        List<Beneficiaire> bens= new ArrayList<Beneficiaire>();
           bens= clientService.getBeneficiaires(idClient);
       Beneficiaire ben = rep.findBenById(id);
        for(int i=0; i<bens.size(); i++) {
            if(bens.get(i).equals(ben)) {
                bens.remove(ben);

            }
        }

             // ;
    }*/
    public List<Beneficiaire> getBeneficiairesByAgent(Integer id) {
        List<Beneficiaire> bens= new ArrayList<Beneficiaire>();
        bens = clientService.getBeneficiaires(id);
        if(bens.isEmpty())  throw new ClientException("Aucun compte trouvé");

        return bens;

    }

    public void addBeneficiaire(Integer id,Beneficiaire beneficiaire ) {
        List<Beneficiaire> bens= new ArrayList<Beneficiaire>();

        bens = clientService.getBeneficiaires(id);
        for(int i=0; i<bens.size(); i++) {
            if(bens.get(i).getAccount_number().equals(beneficiaire.getAccount_number())) {
                throw new ClientException("ce beneficiaire exist deja");
            }
        }
        Beneficiaire beneficiairedb = new Beneficiaire();
        beneficiairedb.setAccount_number(beneficiaire.getAccount_number());
        beneficiairedb.setFirstName(beneficiaire.getFirstName());
        beneficiairedb.setLastName(beneficiaire.getLastName());
        beneficiairedb.setPhoneNumber(beneficiaire.getPhoneNumber());
        beneficiairedb.setParent(clientService.getClientbyId(id));

       // bens.add(beneficiairedb);

        rep.save(beneficiairedb);
    }
}
