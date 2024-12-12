package pl.lodz.p.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import pl.lodz.p.dto.UuidDTO;
import pl.lodz.p.model.user.Client;
import pl.lodz.p.service.implementation.ClientService;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@ApplicationScoped
@Path("/REST/api/client")
public class ClientController {
    @Inject
    ClientService clientServiceImplementation;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClient(@Valid Client client) {
        try {
            Client createdClient = clientServiceImplementation.createClient(client);
            return Response.status(Response.Status.CREATED).entity(createdClient).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();}
//        } catch (Exception e) {
//            return Response.status(Response.Status.CONFLICT)
//                    .entity("Client with username " + client.getUsername() + " already exists! Error: " + e.getMessage())
//                    .build();
//        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        try {
            List<Client> clients;
            try {
                clients = clientServiceImplementation.getAllClients();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No clients found").build();
            }
            return Response.status(Response.Status.OK).entity(clients).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
//
//    @GetMapping("/{uuid}")//tested
//    public ResponseEntity<Object> getUser(@PathVariable("uuid") UuidDTO uuid) {
//        try {
//            Client client;
//            try {
//                client = clientServiceImplementation.getClient(uuid.uuid());
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found");
//            }
//            return ResponseEntity.status(HttpStatus.OK).body(client);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
//
//    @PutMapping("/{uuid}")//fail not tested
//    public ResponseEntity<Object> updateUser(@PathVariable("uuid") UuidDTO uuid, @RequestBody Map<String, Object> fieldsToUpdate, BindingResult bindingResult) {
//        try {
//            if(bindingResult.hasErrors()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
//            }
//            clientServiceImplementation.updateClient(uuid.uuid(), fieldsToUpdate);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been updated");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
//
//    @PutMapping("/deactivate/{uuid}")//tested
//    public ResponseEntity<Object> deactivateUser(@PathVariable("uuid") UuidDTO uuid) {
//        try {
//            try {
//                clientServiceImplementation.deactivateClient(uuid.uuid());
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No client found");
//            }
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been deactivated");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
//
//    @PutMapping("/activate/{uuid}")//tested
//    public ResponseEntity<Object> activateUser(@PathVariable("uuid") UuidDTO uuid) {
//        try {
//            try {
//                clientServiceImplementation.activateClient(uuid.uuid());
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No client found");
//            }
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with uuid " + uuid.uuid() + " has been activated");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
//
//    @GetMapping("findClient/{username}")//tested
//    public ResponseEntity<Object> findClient(@PathVariable("username") String username) {
//        try {
//            Client client;
//            try {
//                client = clientServiceImplementation.getClientByUsername(username);
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No client found");
//            }
//            return ResponseEntity.status(HttpStatus.OK).body(client);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
//
//    @GetMapping("findClients/{username}")//tested
//    public ResponseEntity<Object> findClients(@PathVariable("username") String username) {
//        try {
//            List<Client> clients;
//            try {
//                clients = clientServiceImplementation.getClientsByUsername(username);
//            } catch (RuntimeException ex) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients matching");
//            }
//            return ResponseEntity.status(HttpStatus.OK).body(clients);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//    }
}