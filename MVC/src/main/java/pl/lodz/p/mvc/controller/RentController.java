package pl.lodz.p.mvc.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.lodz.p.mvc.dto.ClientDTO;
import pl.lodz.p.mvc.dto.EndRentDTO;
import pl.lodz.p.mvc.dto.RentDTO;
import pl.lodz.p.mvc.model.Rent;
import pl.lodz.p.mvc.service.implementation.ClientService;
import pl.lodz.p.mvc.service.implementation.RentService;
import pl.lodz.p.mvc.service.implementation.VMachineService;

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
        model.addAttribute("currentPage", "/rent");
        return "rents";

    }

    @GetMapping("/{uuid}")
    public String getRent(@PathVariable("uuid") UUID uuid, Model model) {
        model.addAttribute("rent", rentService.getRent(uuid));
        model.addAttribute("currentPage", "/rent/" + uuid);
        return "rent";
    }

    @GetMapping("endRent/{uuid}")
    public String endRent(@PathVariable("uuid") UUID uuid, Model model) {
        try {
            rentService.endRent(uuid, new EndRentDTO(LocalDateTime.now()));
            model.addAttribute("rents", rentService.getAllRents());
            model.addAttribute("currentPage", "/rent/endRent/" + uuid);
            return "redirect:/rent";
        } catch (HttpClientErrorException e) {
            model.addAttribute("rents", rentService.getAllRents());
            model.addAttribute("currentPage", "/rent/endRent/" + uuid);
            model.addAttribute("error", e.getResponseBodyAsString());
            return "redirect:/rent"; //TODO zmienić
        }
    }

    @GetMapping("/create")
    public String rentCreator(Model model) {
        model.addAttribute("rent", new RentDTO());
        model.addAttribute("vms", rentService.getAllVMachinesHelper());
        model.addAttribute("clients", rentService.getAllClientsHelper());
        model.addAttribute("currentPage", "/rent/create");
        return "createRent";
    }

    @PostMapping("/create")
    public String rentCreator(@ModelAttribute("rent") RentDTO rent, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "createRent";
        } else {
            Rent r = rentService.createRent(rent);
            model.addAttribute("currentPage", "/rent/" + r.getEntityId().getUuid());
            return "redirect:/rent/" + r.getEntityId().getUuid();
        }
    }
}
