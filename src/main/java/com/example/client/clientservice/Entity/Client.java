package com.example.client.clientservice.Entity;

import com.example.client.clientservice.Entity.listeners.ClientListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@EntityListeners({ClientListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_email" , columnNames = {"email"}),
        @UniqueConstraint(name = "unique_idCard" , columnNames = {"idCard"}),
        @UniqueConstraint(name = "unique_phoneNumber" , columnNames = {"phoneNumber"})
})
@Builder
public class Client implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    private String title;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String password;
    private String birthday;
    private String idCard;
    private Boolean validity_of_IDCard ;
    private String phoneNumber;
    private String role;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    @JoinColumn(name = "id_Client" , referencedColumnName = "id")
    @OneToMany(targetEntity = Compte.class,cascade=CascadeType.ALL)
     List<Compte> compte;

    @OneToMany(targetEntity = Beneficiaire.class ,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent" , referencedColumnName = "id")
    private List<Beneficiaire> beneficiaires;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
