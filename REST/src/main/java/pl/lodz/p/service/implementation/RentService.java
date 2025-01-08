package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.dto.RentDTO;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.model.Rent;
import pl.lodz.p.model.user.Client;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.repository.RentRepository;
import pl.lodz.p.service.IRentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentService implements IRentService {

    RentRepository repo;
    private final RestTemplate restTemplate;

    @Override
    public Rent createRent(RentDTO rentDTO) {
        Client client = getClientById(rentDTO.getClientId());
        VMachine vm = getVMachineById(rentDTO.getVmId());
        if(client == null) {
            throw new RuntimeException("Client not found");
        }
        if(vm == null) {
            throw new RuntimeException("VMachine not found");
        }
        if(getVMachineById(rentDTO.getVmId()).isRented() > 0){
            throw new RuntimeException("VMachine already rented");
        }
        if(getClientById(rentDTO.getClientId()).getCurrentRents()>getClientById(rentDTO.getClientId()).getClientType().getMaxRentedMachines()){
            throw new RuntimeException("Client is not permitted to rent more machines: " + client.getCurrentRents() + " > " + client.getClientType().getMaxRentedMachines() +1);
        }
        Rent rent = new Rent(client, vm, rentDTO.getStartTime());
        repo.add(rent);
        return rent;
    }

    @Override
    public List<Rent> getAllRents() {
        List<Rent> rents = repo.getRents();
        if(rents == null || rents.isEmpty()) {
            throw new RuntimeException("Rents not found");
        }
        return rents;
    }

    @Override
    public List<Rent> getActiveRents() {
        List<Rent> activeRents = repo.findBy("endTime", null);
        if(activeRents == null || activeRents.isEmpty()) {
            throw new RuntimeException("Active rents not found");
        }
        return activeRents;
    }

    @Override
    public List<Rent> getArchivedRents() {
        List<Rent> archivedRents = repo.findByNegation("endTime", null);
        if(archivedRents == null || archivedRents.isEmpty()) {
            throw new RuntimeException("Archived rents not found");
        }
        return archivedRents;
    }

    @Override
    public Rent getRent(UUID uuid) {
        Rent rent = repo.getRentByID(new MongoUUID(uuid));
        if(rent == null) {
            throw new RuntimeException("Rent with UUID:" + uuid + " not found");
        }
        return rent;
    }

    @Override
    public List<Rent> getClientAllRents(UUID uuid) {
        List<Rent> allRents = repo.getClientRents(new MongoUUID(uuid));
        if(allRents == null || allRents.isEmpty()) {
            throw new RuntimeException("Client with UUID:" + uuid + " does not have any rents");
        }
        return allRents;
    }

    @Override
    public List<Rent> getClientActiveRents(UUID uuid) {
        List<Rent> activeRents = repo.getClientRents(new MongoUUID(uuid),true);
        if(activeRents == null || activeRents.isEmpty()) {
            throw new RuntimeException("Client with UUID:" + uuid + " does not have active rents");
        }
        return activeRents;
    }

    @Override
    public List<Rent> getClientArchivedRents(UUID uuid) {
        List<Rent> archivedRents = repo.getClientRents(new MongoUUID(uuid),false);
        if(archivedRents == null || archivedRents.isEmpty()) {
            throw new RuntimeException("Client with UUID:" + uuid + " does not have archived rents");
        }
        return archivedRents;
    }

    @Override
    public Rent getVMachineActiveRent(UUID uuid) {
        Rent activeRent = repo.getVMachineRents(new MongoUUID(uuid),true).getFirst();
        if(activeRent == null) {
            throw new RuntimeException("VMachine with UUID:" + uuid + " is not currently rented");
        }
        return activeRent;
    }

    @Override
    public List<Rent> getVMachineArchivedRents(UUID uuid) {
        List<Rent> archivedRents = repo.getVMachineRents(new MongoUUID(uuid),false);
        if(archivedRents == null || archivedRents.isEmpty()) {
            throw new RuntimeException("VMachine with UUID:" + uuid + " does not have archived rents");
        }
        return archivedRents;
    }

    @Override
    public void endRent(UUID uuid, LocalDateTime endDate) {
        repo.endRent(new MongoUUID(uuid), endDate);
    }

    @Override
    public void removeRent(UUID uuid) {
        repo.remove(new MongoUUID(uuid));
    }

    //private helper methods
    private Client getClientById(UUID clientId) {
        String url = "http://localhost:8081/REST/api/client/" + clientId;
        try {
            return restTemplate.getForObject(url, Client.class);
        } catch (Exception e) {
            throw new RuntimeException("Request GET http://localhost:8081/REST/api/client/" + clientId + " failed: " + e);
        }
    }

    private VMachine getVMachineById(UUID vMachineId) {
        String url = "http://localhost:8081/REST/api/vmachine/" + vMachineId;
        try {
            return restTemplate.getForObject(url, VMachine.class);
        } catch (Exception e) {
            throw new RuntimeException("Request GET http://localhost:8081/REST/api/vmachine/" + vMachineId + " failed: " + e);
        }
    }
}

