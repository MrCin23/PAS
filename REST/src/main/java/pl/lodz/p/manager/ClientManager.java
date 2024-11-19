package pl.lodz.p.manager;


import pl.lodz.p.model.Client;
import pl.lodz.p.model.ClientType;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.repository.ClientRepository;

import java.util.List;
import java.util.Map;

//ClientManager jako Singleton
public final class ClientManager {
    private static ClientManager instance;
    private final ClientRepository clientsRepository;

    public ClientManager() {
        clientsRepository = new ClientRepository();
    }

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void registerExistingClient(Client client) {
        clientsRepository.add(client);
    }

    public void registerClient(String firstName, String surname, String username, String emailAddress, ClientType clientType) {
        Client client = new Client(firstName, surname, username, emailAddress, clientType);
        registerExistingClient(client);
    }

    public void unregisterClient(Client client) {
        clientsRepository.remove(client);
    }

    public void update(MongoUUID uuid, Map<String, Object> fieldsToUpdate) {
        clientsRepository.update(uuid, fieldsToUpdate);
    }

    public void update(MongoUUID uuid, String field, Object value) {
        clientsRepository.update(uuid, field, value);
    }

    //METHODS-----------------------------------
//TODO
    public String getAllClientsReport() {
        return this.clientsRepository.getClients().toString();
    }
    public List<Client> getAllClients() {
        return this.clientsRepository.getClients();
    }
    public Client getClient(MongoUUID uuid) {
        return (Client) clientsRepository.getClientByID(uuid);
    }

    public int getClientsAmount() {
        return clientsRepository.getClients().size();
    }
}

