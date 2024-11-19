package pl.lodz.p.service;

import pl.lodz.p.model.VMachine;

import java.util.List;

public interface VMachineService {
    VMachine createVMachine(VMachine vm);

    List<VMachine> getAllVMachines();
}
