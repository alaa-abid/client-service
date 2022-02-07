package com.example.client.clientservice.Service.impl;

import com.example.client.clientservice.DTO.ClientDTO;
import com.example.client.clientservice.Entity.Beneficiaire;
import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Entity.Compte;
import com.example.client.clientservice.Exception.ClientException;
import com.example.client.clientservice.Exception.NotFoundException;
import com.example.client.clientservice.Handler.RestTemplateResponseErrorHandler;
import com.example.client.clientservice.Repository.ClientRepositry;
import com.example.client.clientservice.Repository.CompteRepository;
import com.example.client.clientservice.Service.ClientService;
import com.example.client.clientservice.VO.MultiTransfer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    CompteRepository rep;
    @Autowired
    private ClientRepositry clientRepositry;

    @Autowired
    private CompteServiceImpl compteService;

    private final String transfer_rest_url = "https://transfer-microserv.herokuapp.com/api_transfer/";

    private Compte initialCompte = new Compte();

    @Autowired
    private RestTemplate restTemplate;

    public List<Client> getClients(Integer id)
    {
        List<Client> clients= new ArrayList<Client>();
        if(id!=null)
            clients.add(clientRepositry.findById(id).orElseThrow(() -> new ClientException("Aucun client avec l'id "+id+" trouvé")));
        else
            clients=clientRepositry.findAll();
        if(clients.isEmpty())  throw new ClientException("Aucun client trouvé");
        return clients;
    }

    public Client getByUsername(String username)
    {
        return clientRepositry.findByUsername(username).orElseThrow(() -> new ClientException("Aucun client avec le username "+username+" trouvé"));
    }
    public List<Beneficiaire> getBeneficiaires(Integer id)
    {
        Client client= clientRepositry.findById(id).get();
       // if(client.getBeneficiaires().isEmpty()) throw new ClientException("Ce client n'a aucun beneficiaire.");
        return client.getBeneficiaires();

    }

    public Client getById(Integer id)
    {
        return clientRepositry.findById(id)
                .orElseThrow(() -> new ClientException("Aucun client avec CIN "+id+" trouvé"));
    }

    public Client getByIdCard(String idCard)
    {
        return clientRepositry.findByidCard(idCard)
                .orElseThrow(() -> new ClientException("Aucun client avec CIN "+idCard+" trouvé"));
    }


    public Integer addClient(Client client) {
        initialCompte.setSolde(10000.00);
       // initialCompte.setProprietaire(client);
        List<Compte> lCompte= new ArrayList<>();

       // client.setCompte(initialCompte);
        Client clientdb = clientRepositry.save(client);

        initialCompte.setNumero(clientdb.getId().toString()+ RandomStringUtils.random(10 - clientdb.getId().toString().length() ,false,true));
        lCompte.add(initialCompte);
        clientdb.setCompte(lCompte);
        clientRepositry.save(clientdb);

        return client.getId();

    }


    public ResponseEntity<?> getTransferByReference(String reference) {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate.getForEntity(transfer_rest_url+"UniqueTransfer/"+reference, Object.class,1);
    }

    public ResponseEntity<?> createMultitransferClient(MultiTransfer multitransfer) {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity<?> entity = new HttpEntity<>(multitransfer,headers);
        return restTemplate.postForEntity(transfer_rest_url+"createTransfer/client",entity,Object.class,1);
    }

    @Override
    public Client getClientbyId(Integer id) {
        return clientRepositry.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username){

        return clientRepositry.findFirstByEmail(username).orElseThrow(() -> new ClientException(String.format("Username %s not found.", username))
                );
    }
    public Client getByPhoneNumber(String phonenumber)
    {
        return clientRepositry.findByPhoneNumber(phonenumber)
                .orElseThrow(() -> new ClientException("Aucun client avec ce numero "+phonenumber+" trouvé"));
    }
    public Client getByAccountNumber(String numero)
    {
        Compte compte = rep.findByNumero(numero).orElseThrow(() -> new ClientException("Ce compte n'existe pas"));
        Client client=clientRepositry.findByCompte(compte);
                return client;
    }

    @Override
    public Client VerifyAccount(Integer id, Float amount) {
        Client client = clientRepositry.getById(id);
        Compte compte = compteService.verifySolde(client.getCompte().get(0),amount);
        List<Compte> newSolde=new ArrayList<Compte>();
        newSolde.add(compte);
        client.setCompte(newSolde);

        return clientRepositry.save(client) ;

    }

    public ResponseEntity<?> getAllTransfers(Integer idClient) {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate.getForEntity(transfer_rest_url+"/MultiTransfers/client/"+idClient, Object.class,1);

    }

    public ResponseEntity<?> returnTransferByReference(String reference, String motif) {
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(transfer_rest_url+"/UniqueTransfer/return/"+reference+"?motif="+motif, HttpMethod.PUT,entity,Object.class,1);
    }
}
