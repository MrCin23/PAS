package pl.lodz.p.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import pl.lodz.p.dto.EndRentDTO;
import pl.lodz.p.dto.RentDTO;
import pl.lodz.p.dto.UuidDTO;
import pl.lodz.p.model.Rent;
import pl.lodz.p.service.implementation.RentService;

import javax.naming.Binding;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
@Path("/REST/api/rent")
public class RentController {
    @Inject
    RentService rentService;

    @POST//not tested
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRent(@Valid RentDTO rentDTO) {
        try {
            Rent pom = rentService.createRent(rentDTO);
            return Response.status(Response.Status.CREATED).entity(pom).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
//            return Response.status(Response.Status.CONFLICT).entity(e).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRents() {
        try {
            List<Rent> rents;
            try {
                rents = rentService.getAllRents();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")//not tested
    public Response getRent(@PathParam("uuid") UuidDTO uuid) {
        try {
            Rent rent;
            try {
                rent = rentService.getRent(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rent found").build();
            }
            return Response.status(Response.Status.OK).entity(rent).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/active")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveRents() {
        try {
            List<Rent> rents;
            try {
                rents = rentService.getActiveRents();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/archived")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArchivedRents() {
        try {
            List<Rent> rents;
            try {
                rents = rentService.getArchivedRents();
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/end/{uuid}")//not tested
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response endRent(@PathParam("uuid") UuidDTO uuid, @Valid EndRentDTO endTimeDTO) {
        try {
            try{
                rentService.endRent(uuid.uuid(), endTimeDTO.getEndTime());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }

    }

    @GET
    @Path("/active/client/{uuid}")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientActiveRents(@PathParam("uuid") UuidDTO uuid){
        try {
            List<Rent> rents;
            try {
                rents = rentService.getClientActiveRents(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/active/vmachine/{uuid}")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVMachineActiveRent(@PathParam("uuid") UuidDTO uuid){
        try {
            Rent rent;
            try {
                rent = rentService.getVMachineActiveRent(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rent).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/archived/client/{uuid}")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientArchivedRents(@PathParam("uuid") UuidDTO uuid){
        try {
            List<Rent> rents;
            try {
                rents = rentService.getClientArchivedRents(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/archived/vmachine/{uuid}")//not tested
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVMachineArchivedRents(@PathParam("uuid") UuidDTO uuid){
        try {
            List<Rent> rents;
            try {
                rents = rentService.getVMachineArchivedRents(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.NOT_FOUND).entity("No rents found").build();
            }
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")//not tested
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRent(@PathParam("uuid") UuidDTO uuid) {
        try {
            try {
                rentService.removeRent(uuid.uuid());
            } catch (RuntimeException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No rents found").build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
