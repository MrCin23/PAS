package pl.lodz.p.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.dto.UuidDTO;
import pl.lodz.p.model.Client;
import pl.lodz.p.service.implementation.ClientService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
@Validated
public class ClientController {

    private ClientService clientServiceImplementation;

    @PostMapping//tested
    public ResponseEntity<Object> createClient(@Valid @RequestBody Client client, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(clientServiceImplementation.createClient(client));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Client with username " + client.getUsername() + " already exists! Error code: " + ex);
        }
    }

    @GetMapping//tested
    public ResponseEntity<Object> getAllClients() {
        try {
            List<Client> clients;
            try {
                clients = clientServiceImplementation.getAllClients();
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(clients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/{uuid}")//tested
    public ResponseEntity<Object> getUser(@PathVariable("uuid") UuidDTO uuid) {
        try {
            Client client;
            try {
                client = clientServiceImplementation.getClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(client);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{uuid}")//fail not tested
    public ResponseEntity<Object> updateUser(@PathVariable("uuid") UuidDTO uuid, @RequestBody Map<String, Object> fieldsToUpdate, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
            }
            clientServiceImplementation.updateClient(uuid.uuid(), fieldsToUpdate);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been updated");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/deactivate/{uuid}")//tested
    public ResponseEntity<Object> deactivateUser(@PathVariable("uuid") UuidDTO uuid) {
        try {
            try {
                clientServiceImplementation.deactivateClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No client found");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been deactivated");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/activate/{uuid}")//tested
    public ResponseEntity<Object> activateUser(@PathVariable("uuid") UuidDTO uuid) {
        try {
            try {
                clientServiceImplementation.activateClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No client found");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been activated");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("findClient/{username}")//tested
    public ResponseEntity<Object> findClient(@PathVariable("username") String username) {
        try {
            Client client;
            try {
                client = clientServiceImplementation.getClientByUsername(username);
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(client);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("findClients/{username}")//tested
    public ResponseEntity<Object> findClients(@PathVariable("username") String username) {
        try {
            List<Client> clients;
            try {
                clients = clientServiceImplementation.getClientsByUsername(username);
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients matching");
            }
            return ResponseEntity.status(HttpStatus.OK).body(clients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}