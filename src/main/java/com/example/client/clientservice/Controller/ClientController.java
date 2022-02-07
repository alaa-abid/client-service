package com.example.client.clientservice.Controller;
import com.example.client.clientservice.Controller.Converter.ClientConverter;
import com.example.client.clientservice.DTO.AuthenticationDTO;
import com.example.client.clientservice.DTO.AuthenticationTokenDTO;
import com.example.client.clientservice.DTO.ClientDTO;
import com.example.client.clientservice.Entity.Client;
import com.example.client.clientservice.Repository.CompteRepository;
import com.example.client.clientservice.Service.impl.AuthenticationServiceImpl;
import com.example.client.clientservice.Service.impl.ClientServiceImpl;
import com.example.client.clientservice.VO.MultiTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(allowCredentials = "true",  originPatterns = "*")
@RequestMapping("/api_client")
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private final ClientConverter clientConverter;

    @Autowired
    public ClientController(ClientConverter clientConverter) {
        this.clientConverter = clientConverter;
    }

    //GET
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients(Integer id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientConverter.convertToDTOs(clientService.getClients(id)));
    }
    @GetMapping("/ph/{phoneNumber}")
    public ResponseEntity<ClientDTO> getByPhoneNumber(@PathVariable(name="phoneNumber") String phonenumber ) {
        return ResponseEntity
                .status(HttpStatus.OK).
                body(clientConverter.convertToDTO(clientService.getByPhoneNumber(phonenumber)));
    }
    // getByNumeroCompte
    @GetMapping("/nc/{numero}")
    public ResponseEntity<ClientDTO> getByNumeroCompte(@PathVariable(name="numero") String numero ) {

        return ResponseEntity
                .status(HttpStatus.OK).
                body(clientConverter.convertToDTO(clientService.getByAccountNumber(numero)));
    }


//getverifyAccount id+amount body
    @GetMapping("/client/{id}/{amount}")
    public ResponseEntity<?> getVerifyAccount(@PathVariable(name = "id") Integer id, @PathVariable(name = "amount") Float amount){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientService.VerifyAccount(id,amount));
    }


    @GetMapping("/cin/{cin}")
    public ResponseEntity<ClientDTO> getByIdCard(@PathVariable(name="cin") String IdCard)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientConverter.convertToDTO(clientService.getByIdCard(IdCard)));
    }



    @PostMapping("/login")
    public AuthenticationTokenDTO login(@Valid @RequestBody AuthenticationDTO authenticationDto) {
        return authenticationService.authenticate(authenticationDto);
    }
    //get
    ///client+ id_client


    //POST
    @PostMapping("/client")
    public ResponseEntity<Integer> addClient(@RequestBody Client client)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.addClient(client));
    }


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("id") Integer id) {
        Client client = clientService.getClientbyId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientConverter.convertToDTO(client));
    }



}
