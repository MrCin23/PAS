package pl.lodz.p.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.model.Client;
import pl.lodz.p.service.implementation.ClientServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientServiceImplementation clientServiceImplementation;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientServiceImplementation.createClient(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientServiceImplementation.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
