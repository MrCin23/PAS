package pl.lodz.p.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.model.Client;
import pl.lodz.p.service.implementation.ClientServiceImplementation;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientServiceImplementation clientServiceImplementation;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientServiceImplementation.createClient(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
}
