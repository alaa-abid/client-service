package com.example.client.clientservice.DTO;

import com.example.client.clientservice.Entity.Compte;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ClientDTO {
    @NotNull
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String idCard;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private List<Compte> compte;

    private String birthday;


}
