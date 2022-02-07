package com.example.client.clientservice.Service;

import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.VO.MultiTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ClientService extends UserDetailsService {
    public Client VerifyAccount(Integer id, Float amount);
    public List<Client> getClients(Integer id);
    public Client getByUsername(String username);
    public Client getById(Integer id);
    public Client getByIdCard(String idCard);
    public Integer addClient(Client client);
    public ResponseEntity<?> getTransferByReference(String reference);
    public ResponseEntity<?> createMultitransferClient(MultiTransfer multitransfer);

    Client getClientbyId(Integer id);
}