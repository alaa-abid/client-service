package com.example.client.clientservice.Repository;

import com.example.client.clientservice.Entity.Beneficiaire;
import com.example.client.clientservice.Entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
   Optional <Compte> findByNumero(String numero);
}