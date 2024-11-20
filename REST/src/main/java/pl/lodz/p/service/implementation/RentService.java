package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.dto.RentDTO;
import pl.lodz.p.model.Rent;
import pl.lodz.p.model.Client;
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
        Rent rent = new Rent(client, vm, rentDTO.getStartTime());
        repo.add(rent);
        return rent;
    }

    @Override
    public List<Rent> getAllRents() {
        return repo.getRents();
    }

    @Override
    public List<Rent> getActiveRents() {
        return List.of();
    }

    @Override
    public List<Rent> getArchivedRents() {
        return List.of();
    }

    @Override
    public Rent getRent(UUID id) {
        return null;
    }

    @Override
    public List<Rent> getClientActiveRents() {
        return List.of();
    }

    @Override
    public List<Rent> getClientArchivedRents() {
        return List.of();
    }

    @Override
    public Rent getVMachineActiveRent() {
        return null;
    }

    @Override
    public List<Rent> getVMachineArchivedRents() {
        return List.of();
    }

    //private helper methods
    private Client getClientById(UUID clientId) {
        String url = "http://localhost:8080/REST/api/client/" + clientId;
        try {
            return restTemplate.getForObject(url, Client.class);
        } catch (Exception e) {
            throw new RuntimeException("Request GET http://localhost:8080/REST/api/client/" + clientId + " failed: " + e);
        }
    }

    private VMachine getVMachineById(UUID vMachineId) {
        String url = "http://localhost:8080/REST/api/vmachine/" + vMachineId;
        try {
            return restTemplate.getForObject(url, VMachine.class);
        } catch (Exception e) {
            throw new RuntimeException("Request GET http://localhost:8080/REST/api/vmachine/" + vMachineId + " failed: " + e);
        }
    }
}

