package pl.lodz.p.mvc.service;

import pl.lodz.p.mvc.dto.EndRentDTO;
import pl.lodz.p.mvc.dto.RentDTO;
import pl.lodz.p.mvc.model.Rent;
import pl.lodz.p.mvc.model.VMachine;
import pl.lodz.p.mvc.model.user.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IRentService {

    Rent createRent(RentDTO rentDTO);

    List<Rent> getAllRents();

    List<Rent> getActiveRents();

    List<Rent> getArchivedRents();

    Rent getRent(UUID id);

    List<Rent> getClientActiveRents(UUID uuid);

    List<Rent> getClientArchivedRents(UUID uuid);

    Rent getVMachineActiveRent(UUID uuid);

    List<Rent> getVMachineArchivedRents(UUID uuid);

//    void endRent(UUID uuid, LocalDateTime endDate);
    void endRent(UUID uuid, EndRentDTO endRentDTO);

    void removeRent(UUID uuid);

    List<Client> getAllClientsHelper();

    List<VMachine> getAllVMachinesHelper();
}
