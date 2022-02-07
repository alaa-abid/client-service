package com.example.client.clientservice.Repository;

import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepositry extends JpaRepository<Client,Integer> {

    Optional<Client> findByUsername(String username);

    Optional<Client> findByidCard(String idCard);

    Optional<Client> findFirstByEmail(String username);

   Optional <Client> findByPhoneNumber(String phoneNumber);



    Client findByCompte(Compte numerocompte);
}
