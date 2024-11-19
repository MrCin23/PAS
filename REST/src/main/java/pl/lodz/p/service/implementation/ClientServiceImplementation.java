package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.Client;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.repository.ClientRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientServiceImplementation implements pl.lodz.p.service.ClientService {

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
        repo.update(new MongoUUID(uuid), fieldsToUpdate);
        //TODO
    }

    @Override
    public void activateClient(UUID uuid) {
        repo.update(new MongoUUID(uuid), "active", true);
    }

    @Override
    public void deactivateClient(UUID uuid) {
        repo.update(new MongoUUID(uuid), "active", false);
    }

    @Override
    public void deleteClient(UUID uuid) {
        repo.remove(repo.getClientByID(new MongoUUID(uuid)));
    }

    @Override
    public Client getClientByUsername(String username) {
        return repo.getClientByUsername(username);
    }

    @Override
    public List<Client> getClientsByUsername(String username) {
        return repo.getClientsByUsername(username);
    }
}
