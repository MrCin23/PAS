package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.model.Rent;
import pl.lodz.p.service.implementation.RentServiceImplementation;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rent")
public class RentController {
    private RentServiceImplementation rentService;

    @PostMapping
    public ResponseEntity<Rent> createRent(@RequestBody Rent rent) {
        Rent savedRent = rentService.createRent(rent);
        return new ResponseEntity<>(savedRent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        List<Rent> rents = rentService.getAllRents();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }
}
