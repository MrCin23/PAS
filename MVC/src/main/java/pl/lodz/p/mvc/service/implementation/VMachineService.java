package pl.lodz.p.mvc.service.implementation;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.mvc.model.VMachine;
import pl.lodz.p.mvc.service.IVMachineService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VMachineService implements IVMachineService {

    private final RestTemplate restTemplate = new RestTemplate();
    static String API_URL = "http://localhost:8081/REST/api/vmachine";

    @Override
    public List<VMachine> getAllVMachines() {
        return (restTemplate.getForObject(API_URL, List.class));
    }

    @Override
    public VMachine getVMachine(UUID uuid) {
        return restTemplate.getForObject(API_URL + "/" + uuid.toString(), VMachine.class);
    }
}

