package pl.lodz.p.service;

import pl.lodz.p.model.Client;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);

    List<Client> getAllClients();
}
