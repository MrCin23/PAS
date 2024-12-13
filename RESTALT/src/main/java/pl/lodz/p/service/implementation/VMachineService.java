package pl.lodz.p.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.repository.VMachineRepository;
import pl.lodz.p.service.IVMachineService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@ApplicationScoped
@AllArgsConstructor
public class VMachineService implements IVMachineService {
    @Inject
    VMachineRepository repo;

    @Override
    public VMachine createVMachine(VMachine vm) {
        if(repo.getVMachineByID(vm.getEntityId()) == null) {
            repo.add(vm);
            return vm;
        }
        throw new RuntimeException("VMachine with id " + vm.getEntityId() + " already exists");
    }

    @Override
    public List<VMachine> getAllVMachines() {
        List<VMachine> vms = repo.getVMachines();
        if(vms == null || vms.isEmpty()) {
            throw new RuntimeException("No vmachines found");
        }
        return repo.getVMachines();
    }

    @Override
    public VMachine getVMachine(UUID uuid) {
        VMachine vm = repo.getVMachineByID(new MongoUUID(uuid));
        if(vm == null) {
            throw new RuntimeException("VMachine with id " + uuid + " does not exist");
        }
        return repo.getVMachineByID(new MongoUUID(uuid));
    }

    @Override
    public void updateVMachine(UUID uuid, Map<String, Object> fieldsToUpdate) {
        if(repo.getVMachineByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("VMachine with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), fieldsToUpdate);
    }

    @Override
    public void deleteVMachine(UUID uuid) {
        if(repo.getVMachineByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("VMachine with id " + uuid + " does not exist");
        }
        if(repo.getVMachineByID(new MongoUUID(uuid)).getIsRented() > 0) {
            throw new RuntimeException("VMachine with id " + uuid + " is currently rented");
        }
        repo.remove(repo.getVMachineByID(new MongoUUID(uuid)));
    }
}
