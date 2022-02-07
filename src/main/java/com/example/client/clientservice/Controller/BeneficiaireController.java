package com.example.client.clientservice.Controller;

import com.example.client.clientservice.Entity.Beneficiaire;
import com.example.client.clientservice.Exception.AlreadyExistsException;
import com.example.client.clientservice.Exception.NotFoundException;
import com.example.client.clientservice.Service.impl.BeneficiaireServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

@RequestMapping("/api_client")
public class BeneficiaireController {

    @Autowired
    BeneficiaireServiceImpl service;

    public BeneficiaireController(BeneficiaireServiceImpl service) {

        this.service=service;
    }
    //get
    @GetMapping("/beneficiaires")
    public List<Beneficiaire> getBeneficiairesByAPP(@RequestHeader("id") Integer id) throws NotFoundException
    {
        return service.getBeneficiairesByAPP(id);
    }
    //get
    @GetMapping("/beneficiaires/agent/{id}")
    public List<Beneficiaire> getBeneficiairesByAgent(@PathVariable("id") Integer id) throws NotFoundException
    {
        return service.getBeneficiairesByAgent(id);
    }
    //to do : addbeneficiaire
    //post
    @PostMapping("/beneficiaire")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBeneficiaire(@RequestHeader(name="id") Integer id ,@RequestBody Beneficiaire beneficiaire)  throws AlreadyExistsException
    {
        service.addBeneficiaire(id,beneficiaire);
    }

    //DELETE
  /*  @DeleteMapping("/beneficiaire/{id}")
    public void deleteBeneficiaire(@RequestHeader(name = "id") Integer idClient,@PathVariable(name="id") Integer id) throws NotFoundException
    {
         service.removeBeneficiaire(idClient,id);
    }*/
}
