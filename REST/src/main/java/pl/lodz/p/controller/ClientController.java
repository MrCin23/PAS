package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.model.Client;
import pl.lodz.p.service.implementation.ClientServiceImplementation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
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

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Client getUser(@PathVariable("uuid") UUID uuid) {
        return clientServiceImplementation.getClient(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("uuid") UUID uuid, @RequestBody Map<String, Object> fieldsToUpdate) {
        clientServiceImplementation.updateClient(uuid, fieldsToUpdate);
    }

    @PutMapping("/deactivate/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateUser(@PathVariable("uuid") UUID uuid) {
        clientServiceImplementation.deactivateClient(uuid);
    }

    @PutMapping("/activate/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@PathVariable("uuid") UUID uuid) {
        clientServiceImplementation.activateClient(uuid);
    }

    @GetMapping("findClient/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Client findClient(@PathVariable("username") String username) {
        return clientServiceImplementation.getClientByUsername(username);
    }

    @GetMapping("findClients/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> findClients(@PathVariable("username") String username) {
        return clientServiceImplementation.getClientsByUsername(username);
    }

//    @DeleteMapping("/{uuid}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable("uuid") UUID uuid) {
//        clientServiceImplementation.deleteClient(uuid);
//    }



}
