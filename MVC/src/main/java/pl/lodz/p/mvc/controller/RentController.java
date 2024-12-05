package pl.lodz.p.mvc.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.lodz.p.mvc.dto.EndRentDTO;
import pl.lodz.p.mvc.service.implementation.RentService;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/rent")
@Validated
public class RentController {

    private final RentService rentService;

    @GetMapping
    public String getAllRents(Model model) {
        model.addAttribute("rents", rentService.getAllRents());
        return "rents";
    }

    @GetMapping("/{uuid}")
    public String getRent(@PathVariable("uuid") UUID uuid, Model model) {
        model.addAttribute("rent", rentService.getRent(uuid));
        return "rent";
    }

    @GetMapping("endRent/{uuid}")
    public String endRent(@PathVariable("uuid") UUID uuid, Model model) {
        rentService.endRent(uuid, new EndRentDTO(LocalDateTime.now()));
        model.addAttribute("rents", rentService.getAllRents());
        return "rents";
    }
}
