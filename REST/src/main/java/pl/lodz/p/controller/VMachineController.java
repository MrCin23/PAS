package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.service.implementation.VMachineService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vmachine")
public class VMachineController {

    private VMachineService vMachineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VMachine create(@RequestBody VMachine vm) {
        return vMachineService.createVMachine(vm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VMachine> getAll() {
        return vMachineService.getAllVMachines();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public VMachine getVMachine(@PathVariable("uuid") UUID uuid) {
        return vMachineService.getVMachine(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVMachine(@PathVariable("uuid") UUID uuid, @RequestBody Map<String, Object> fieldsToUpdate) {
        vMachineService.updateVMachine(uuid, fieldsToUpdate);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVMachine(@PathVariable("uuid") UUID uuid) {
        vMachineService.deleteVMachine(uuid);
    }
}
