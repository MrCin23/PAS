package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.dto.RentDTO;
import pl.lodz.p.model.Rent;
import pl.lodz.p.service.implementation.RentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rent")
public class RentController {
    private RentService rentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Rent createRent(@RequestBody RentDTO rentDTO) {
        return rentService.createRent(rentDTO);
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        List<Rent> rents = rentService.getAllRents();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }
}
