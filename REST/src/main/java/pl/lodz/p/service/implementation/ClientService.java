package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.user.Client;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.repository.ClientRepository;
import pl.lodz.p.service.IClientService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {

    private ClientRepository repo;

    @Override
    public Client createClient(Client client) {
        if(repo.getClientByID(client.getEntityId()) == null) {
            repo.add(client);
            return client;
        }
        throw new RuntimeException("Client with id " + client.getEntityId() + " already exists");
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = repo.getClients();
        if(clients == null || clients.isEmpty()) {
            throw new RuntimeException("No clients found");
        }
        return repo.getClients();
    }

    @Override
    public Client getClient(UUID uuid) {
        Client client = repo.getClientByID(new MongoUUID(uuid));
        if(client == null) {
            throw new RuntimeException("Client with id " + uuid + " does not exist");
        }
        return client;
    }

    @Override
    public void updateClient(UUID uuid, Map<String, Object> fieldsToUpdate) {
        if(repo.getClientByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("Client with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), fieldsToUpdate);
    }

    @Override
    public void activateClient(UUID uuid) {
        if(repo.getClientByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("Client with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), "active", true);
    }

    @Override
    public void deactivateClient(UUID uuid) {
        if(repo.getClientByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("Client with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), "active", false);
    }

//    @Override
//    public void deleteClient(UUID uuid) {
//        repo.remove(repo.getClientByID(new MongoUUID(uuid)));
//    }

    @Override
    public Client getClientByUsername(String username) {
        if(repo.getClientByUsername(username) == null) {
            throw new RuntimeException("Client with username " + username + " does not exist");
        }
        return repo.getClientByUsername(username);
    }

    @Override
    public List<Client> getClientsByUsername(String username) {
        if(repo.getClientsByUsername(username) == null || repo.getClientsByUsername(username).isEmpty()) {
            throw new RuntimeException("No clients with username " + username + " found");
        }
        return repo.getClientsByUsername(username);
    }
}
