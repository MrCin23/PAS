package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.service.VMachineService;
import pl.lodz.p.service.implementation.VMachineServiceImplementation;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/vmachine")
public class VMachineController {

    private VMachineServiceImplementation vMachineService;

    @PostMapping
    public ResponseEntity<VMachine> create(@RequestBody VMachine vm) {
        VMachine savedVMachine = vMachineService.createVMachine(vm);
        return new ResponseEntity<>(savedVMachine, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VMachine>> getAll() {
        List<VMachine> vm = vMachineService.getAllVMachines();
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
}
