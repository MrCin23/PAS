package pl.lodz.p.mvc.service.implementation;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.mvc.dto.EndRentDTO;
import pl.lodz.p.mvc.dto.RentDTO;
import pl.lodz.p.mvc.model.Rent;
import pl.lodz.p.mvc.model.VMachine;
import pl.lodz.p.mvc.model.user.Client;
import pl.lodz.p.mvc.service.IRentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class RentService implements IRentService {
    private final RestTemplate restTemplate = new RestTemplate();
    static String API_URL = "http://localhost:8081/REST/api/";

    @Override
    public Rent createRent(RentDTO rentDTO) {
        rentDTO.setStartTime(LocalDateTime.now());
        return restTemplate.postForObject(API_URL + "rent", rentDTO, Rent.class);
    }

    @Override
    public List<Rent> getAllRents() {
        return restTemplate.getForObject(API_URL + "rent", List.class);
    }

    @Override
    public Rent getRent(UUID id) {
        return restTemplate.getForObject(API_URL + "rent/" + id, Rent.class);
    }

    @Override
    public List<Rent> getActiveRents() {
        //TODO
        return List.of();
    }

    @Override
    public List<Rent> getArchivedRents() {
        //TODO
        return List.of();
    }

    @Override
    public List<Rent> getClientActiveRents(UUID uuid) {
        //TODO
        return List.of();
    }

    @Override
    public List<Rent> getClientArchivedRents(UUID uuid) {
        //TODO
        return List.of();
    }

    @Override
    public Rent getVMachineActiveRent(UUID uuid) {
        //TODO
        return null;
    }

    @Override
    public List<Rent> getVMachineArchivedRents(UUID uuid) {
        //TODO
        return List.of();
    }

    @Override
    public void endRent(UUID uuid, EndRentDTO endRentDTO) {
        restTemplate.put(API_URL + "rent/end/" + uuid, endRentDTO);
    }

    @Override
    public void removeRent(UUID uuid) {

    }

    @Override
    public List<Client> getAllClientsHelper() {
        return restTemplate.getForObject(API_URL + "client", List.class);
    }

    @Override
    public List<VMachine> getAllVMachinesHelper() {
        return restTemplate.getForObject(API_URL + "vmachine", List.class);
    }
}
