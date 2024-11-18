package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.Client;
import pl.lodz.p.repository.ClientRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImplementation implements pl.lodz.p.service.ClientService {

    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        clientRepository.add(client);
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getClients();
    }

}
