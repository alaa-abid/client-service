package com.example.client.clientservice.Repository;

import com.example.client.clientservice.Entity.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Integer> {
    Beneficiaire findBenById(Integer id);
}
