package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.Client;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.repository.VMachineRepository;
import pl.lodz.p.service.VMachineService;

import java.util.List;

@Service
@AllArgsConstructor
public class VMachineServiceImplementation implements VMachineService {

    VMachineRepository repo;

    @Override
    public VMachine createVMachine(VMachine vm) {
        repo.add(vm);
        return vm;
    }

    @Override
    public List<VMachine> getAllVMachines() {
        return repo.getVMachines();
    }
}
