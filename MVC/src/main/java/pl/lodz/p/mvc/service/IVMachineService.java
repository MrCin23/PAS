package pl.lodz.p.mvc.service;

import pl.lodz.p.mvc.model.VMachine;

import java.util.List;
import java.util.UUID;

public interface IVMachineService {
//    VMachine createVMachine(VMachine vm);

    List<VMachine> getAllVMachines();

    VMachine getVMachine(UUID uuid);

//    void updateVMachine(UUID uuid, Map<String, Object> fieldsToUpdate);
//
//    void deleteVMachine(UUID uuid);
}