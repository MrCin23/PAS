package pl.lodz.p.mvc.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.mvc.dto.ClientDTO;
import pl.lodz.p.mvc.model.user.Client;
import pl.lodz.p.mvc.model.user.Standard;
import pl.lodz.p.mvc.service.implementation.ClientService;

import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/client")
@Validated
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("currentPage", "/client");
        return "clients";
    }

    @GetMapping("/{uuid}")
    public String getVMachine(@PathVariable("uuid") UUID uuid, Model model) {
        model.addAttribute("client", clientService.getClient(uuid));
        model.addAttribute("currentPage", "/client/" + uuid);
        return "client";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("client", new ClientDTO());
        model.addAttribute("currentPage", "/client/register");// /<a href="..">/client</a> ??
        return "registerClient";
    }

    @PostMapping("/register")
    public String createClient(@ModelAttribute("client") ClientDTO client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registerClient";
        }
        Client c = new Client(client.getFirstName(),client.getSurname(),client.getUsername(),client.getEmailAddress(),new Standard());
        clientService.createClient(c);
        model.addAttribute("currentPage", "/client/" + c.getEntityId().toString());
        return "redirect:/client/" + c.getEntityId().toString();
    }

//TODO
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("client", new ClientDTO());
//        return "login";
//    }
//
//    @GetMapping("/login/{username}")
//    public String userPanel(Model model, @PathVariable("username") String username) {
//        model.addAttribute("client", clientService.getClientByUsername(username));//TODO
//        return "login";
//    }



//
//    @GetMapping("/{uuid}/edit")
//    public String showEditForm(@PathVariable("uuid") UUID uuid, Model model) {
//        model.addAttribute("vm", vMachineService.getVMachine(uuid));
//        return "vmachine-form"; // Widok ten sam co dodawanie
//    }
//
//    @PostMapping("/{uuid}/edit")
//    public String updateVMachine(@PathVariable("uuid") UUID uuid, @RequestParam Map<String, String> fieldsToUpdate, Model model) {
//        vMachineService.updateVMachine(uuid, fieldsToUpdate);
//        return "redirect:/vmachine";
//    }
//
//    @PostMapping("/{uuid}/delete")
//    public String deleteVMachine(@PathVariable("uuid") UUID uuid) {
//        vMachineService.deleteVMachine(uuid);
//        return "redirect:/vmachine";
//    }
}
