package pl.lodz.p.mvc.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.mvc.model.VMachine;
import pl.lodz.p.mvc.model.user.Client;
import pl.lodz.p.mvc.service.IClientService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    static String API_URL = "http://localhost:8081/REST/api/client";

    @Override
    public Client createClient(Client client) {
        //todo
        return null;
    }
    @Override
    public List<Client> getAllClients() {
        return (restTemplate.getForObject(API_URL, List.class));
    }

    @Override
    public Client getClient(UUID uuid) {
        return restTemplate.getForObject(API_URL + "/" + uuid.toString(), Client.class);
    }
//
//    @Override
//    public void updateClient(UUID uuid, Map<String, Object> fieldsToUpdate) {
//
//    }
//
//    @Override
//    public void activateClient(UUID uuid) {
//
//    }
//
//    @Override
//    public void deactivateClient(UUID uuid) {
//
//    }
//
//    @Override
//    public Client getClientByUsername(String username) {
//        return null;
//    }
//
//    @Override
//    public List<Client> getClientsByUsername(String username) {
//        return List.of();
//    }
}
