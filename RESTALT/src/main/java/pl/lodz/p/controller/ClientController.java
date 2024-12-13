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
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Client with username " + client.getUsername() + " already exists! Error: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        try {
            List<Client> clients;
            try {
                clients = clientServiceImplementation.getAllClients();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No clients found" + ex.getMessage()).build();
            }
            return Response.status(Response.Status.OK).entity(clients).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/{uuid}")//tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("uuid") UuidDTO uuid) {
        try {
            Client client;
            try {
                client = clientServiceImplementation.getClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No client found").build();
            }
            return Response.status(Response.Status.OK).entity(client).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{uuid}")//fail not tested
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("uuid") UuidDTO uuid, Map<String, Object> fieldsToUpdate) {
        try {
            clientServiceImplementation.updateClient(uuid.uuid(), fieldsToUpdate);
            return Response.status(Response.Status.NO_CONTENT).entity("Client with uuid " + uuid.uuid() + " has been updated").build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/deactivate/{uuid}")//tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateUser(@PathParam("uuid") UuidDTO uuid) {
        try {
            try {
                clientServiceImplementation.deactivateClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No client found").build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity("Client with uuid " + uuid.uuid() + " has been deactivated").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/activate/{uuid}")//tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateUser(@PathParam("uuid") UuidDTO uuid) {
        try {
            try {
                clientServiceImplementation.activateClient(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No client found").build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity("Client with uuid " + uuid.uuid() + " has been activated").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("findClient/{username}")//tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response findClient(@PathParam("username") String username) {
        try {
            Client client;
            try {
                client = clientServiceImplementation.getClientByUsername(username);
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No client found").build();
            }
            return Response.status(Response.Status.OK).entity(client).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("findClients/{username}")//tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response findClients(@PathParam("username") String username) {
        try {
            List<Client> clients;
            try {
                clients = clientServiceImplementation.getClientsByUsername(username);
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No clients matching").build();
            }
            return Response.status(Response.Status.OK).entity(clients).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}