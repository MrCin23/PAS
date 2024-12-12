package pl.lodz.p.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import pl.lodz.p.dto.UuidDTO;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.service.implementation.VMachineService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
@Path("/REST/api/vmachine")
public class VMachineController {
    @Inject
    private VMachineService vMachineService;

    @POST//not tested
    public Response create(@Valid VMachine vm) {
        try {
            return Response.status(Response.Status.CREATED).entity(vMachineService.createVMachine(vm)).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET//not tested
    public Response getAll() {
        try {
            List<VMachine> vms;
            try {
                vms = vMachineService.getAllVMachines();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No vms found").build();
            }
            return Response.status(Response.Status.OK).entity(vms).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @GET
    @Path("/{uuid}")//not tested
    public Response getVMachine(@PathParam("uuid") UuidDTO uuid) {
        try {
            VMachine vm;
            try {
                vm = vMachineService.getVMachine(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No vm found").build();
            }
            return Response.status(Response.Status.OK).entity(vm).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @PUT
    @Path("/{uuid}")//not tested
    public Response updateVMachine(@PathParam("uuid") UuidDTO uuid, Map<String, Object> fieldsToUpdate) {
        try {
            vMachineService.updateVMachine(uuid.uuid(), fieldsToUpdate);//tried converting this to dto to validate, bad idea
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @DELETE
    @Path("/{uuid}")//not tested
    public Response deleteVMachine(@PathParam("uuid") UuidDTO uuid) {
        try {
            try {
                vMachineService.deleteVMachine(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No vm found").build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
