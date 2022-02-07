package com.example.client.clientservice.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaire{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @JsonIgnore
    @JoinColumn(name="parent")
    @ManyToOne
    private Client parent;
    private String account_number;
}