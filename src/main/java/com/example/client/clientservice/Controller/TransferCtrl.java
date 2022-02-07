package com.example.client.clientservice.Controller;

import com.example.client.clientservice.Service.impl.ClientServiceImpl;
import com.example.client.clientservice.VO.MultiTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(allowCredentials = "true",  originPatterns = "*")
@RequestMapping("/api_client")
public class TransferCtrl {

    @Autowired
    ClientServiceImpl clientService;

    //Get Transfer

    @GetMapping("/UniqueTransfer/{reference}")
    public ResponseEntity<?> getTransferByReference(@PathVariable("reference") String reference){
        return (clientService.getTransferByReference(reference));
    }
    @GetMapping("/MultiTransfers/client")
    public ResponseEntity<?> getAllTransfers(@RequestHeader("id") Integer idClient){
        return (clientService.getAllTransfers(idClient));
    }


    //POST Transfer
    @PostMapping("/createTransfer")
    public ResponseEntity<?> createtransferClient(@Valid @RequestBody MultiTransfer multitransfer
            , @RequestHeader("id") Integer id){

       multitransfer.setId_client(id);
        clientService.VerifyAccount(id,multitransfer.getTotal_amount());

        return clientService.createMultitransferClient(multitransfer);
    }


    @PutMapping("/UniqueTransfer/return/{reference}")
    public ResponseEntity<?> returnTransferByReference(@PathVariable("reference") String reference,
                                                                      @RequestParam("motif") String motif){
        return clientService.returnTransferByReference(reference,motif);
    }

}
