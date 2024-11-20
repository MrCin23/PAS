package pl.lodz.p.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.dto.EndRentDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rent createRent(@RequestBody RentDTO rentDTO) {
        return rentService.createRent(rentDTO);
    }

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        List<Rent> rents = rentService.getAllRents();
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Rent getRent(@PathVariable("uuid") UUID uuid) {
        return rentService.getRent(uuid);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<Rent> getActiveRents() {
        return rentService.getActiveRents();
    }

    @GetMapping("/archived")
    @ResponseStatus(HttpStatus.OK)
    public List<Rent> getArchivedRents() {
        return rentService.getArchivedRents();
    }

    @PutMapping("/end/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void endRent(@PathVariable("uuid") UUID uuid, @RequestBody EndRentDTO endTimeDTO) {
        rentService.endRent(uuid, endTimeDTO.getEndTime());
    }
}
