package com.example.client.clientservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_numero" , columnNames = {"numero"}),
})
public class Compte {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Integer id;

    private String numero;
    private double solde;
    private LocalDateTime created_at = LocalDateTime.now();



   /* @JsonIgnore
    @Column(name="VIREMENTS_ENVOYES_COMPTE")
    @OneToMany(mappedBy="debiteur",cascade=CascadeType.ALL)
    List<Transfer> TransfersEnvoyes;

    @JsonIgnore
    @Column(name="VIREMENTS_RECUS_COMPTE")
    @OneToMany(mappedBy="creancier",cascade=CascadeType.ALL)
    List<Transfer> TransfersRecus;*/






}
