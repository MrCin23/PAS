//package pl.lodz.p;
//
//import com.mongodb.client.model.IndexOptions;
//import io.quarkus.runtime.Startup;
//import jakarta.annotation.PostConstruct;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import lombok.Getter;
//import lombok.Setter;
//import org.bson.Document;
//import pl.lodz.p.manager.ClientManager;
//import pl.lodz.p.manager.RentManager;
//import pl.lodz.p.manager.VMachineManager;
//import pl.lodz.p.model.*;
//import pl.lodz.p.model.user.Premium;
//import pl.lodz.p.model.user.Client;
//import pl.lodz.p.model.user.Standard;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@Startup
//@ApplicationScoped
//public class DataInitializer {
//    private ClientManager clientMan = ClientManager.getInstance();
//    private RentManager rentService = RentManager.getInstance();
//    private VMachineManager vmMan = VMachineManager.getInstance();
//    List<Client> clients = new ArrayList<>();
//    List<Rent> rents = new ArrayList<>();
//    List<VMachine> vms = new ArrayList<>();
//    @PostConstruct
//    public void init(){
//        clients = new ArrayList<>();
//        rents = new ArrayList<>();
//        vms = new ArrayList<>();
//        initClient();
//        initVM();
//        initRent();
//    }
//
//    public DataInitializer() {
//        init();
//    }
//
//    public void dropAndCreateClient(){
//        clientMan.getRepo().getDatabase().getCollection("clients").drop();
//        clientMan.getRepo().getDatabase().createCollection("clients");
//        clientMan.getRepo().getDatabase().getCollection("clients").createIndex(
//                new Document("username", 1),
//                new IndexOptions().unique(true)
//        );
//    }
//
//    public void dropAndCreateVMachine(){
//        vmMan.getRepo().getDatabase().getCollection("vMachines").drop();
//        vmMan.getRepo().getDatabase().createCollection("vMachines");
//    }
//
//    public void dropAndCreateRent(){
//        rentService.getRepo().getDatabase().getCollection("rents").drop();
//        rentService.getRepo().getDatabase().getCollection("clients").drop();
//        rentService.getRepo().getDatabase().getCollection("vMachines").drop();
//        rentService.getRepo().getDatabase().createCollection("vMachines");
//        rentService.getRepo().getDatabase().createCollection("clients");
//        rentService.getRepo().getDatabase().createCollection("rents");
//    }
//
//    public void initClient(){
//        clients.add(new Client("Bart", "Fox", "Idontexist", "BFox@tul.com", new Premium()));
//        clients.add(new Client("Michael", "Corrugated", "DON_IAS", "MCorrugated@ias.pas.p.lodz.pl", new Premium()));
//        clients.add(new Client("Matthew", "Tar", "MTar", "MTar@TarVSCorrugated.com", new Premium()));
//        clients.add(new Client("Martin", "Bricky", "Brickman", "IntelEnjoyer@whatisonpage4035.com", new Standard()));
//        clients.add(new Client("Juan", "Escobar", "JEscobar", "JEscobar@colombianSnow.com", new Standard()));
//        clientMan.registerExistingClient(clients.get(0));
//        clientMan.registerExistingClient(clients.get(1));
//        clientMan.registerExistingClient(clients.get(2));
//        clientMan.registerExistingClient(clients.get(3));
//        clientMan.registerExistingClient(clients.get(4));
//    }
//
//
//    public void initVM(){
//        vms.add(new AppleArch(4, "4GB"));
//        vms.add(new AppleArch(24, "128GB"));
//        vms.add(new x86(8, "8GB", "AMD"));
//        vms.add(new x86(16, "32GB", "Intel"));
//        vms.add(new x86(128, "256GB", "Other"));
//        vms.add(new x86(128, "256GB", "Other"));
//        vmMan.registerExistingVMachine(vms.get(0));
//        vmMan.registerExistingVMachine(vms.get(1));
//        vmMan.registerExistingVMachine(vms.get(2));
//        vmMan.registerExistingVMachine(vms.get(3));
//        vmMan.registerExistingVMachine(vms.get(4));
//        vmMan.registerExistingVMachine(vms.get(5));
//    }
//
//    public void initRent(){
//        rents.add(new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024,11,21,21,37)));
//        rents.add(new Rent(clients.get(0), vms.get(2), LocalDateTime.of(2024,10,26,21,37)));
//        rents.add(new Rent(clients.get(3), vms.get(3), LocalDateTime.of(2023,10,26,21,37)));
//        rents.add(new Rent(clients.get(4), vms.get(1), LocalDateTime.of(2023,11,11,11,11)));
//        rents.add(new Rent(clients.get(1), vms.get(4), LocalDateTime.of(2011,11,11,11,11)));
//        rentService.registerExistingRent(rents.get(0));
//        rentService.registerExistingRent(rents.get(1));
//        rentService.registerExistingRent(rents.get(2));
//        rentService.registerExistingRent(rents.get(3));
//        rentService.registerExistingRent(rents.get(4));
//    }
//}

package pl.lodz.p;

import com.mongodb.client.model.IndexOptions;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import pl.lodz.p.dto.RentDTO;
import pl.lodz.p.model.*;
import pl.lodz.p.model.user.Client;
import pl.lodz.p.model.user.Premium;
import pl.lodz.p.model.user.Standard;
import pl.lodz.p.service.implementation.ClientService;
import pl.lodz.p.service.implementation.RentService;
import pl.lodz.p.service.implementation.VMachineService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Startup
@ApplicationScoped
public class DataInitializer {

    @Inject
    ClientService clientService;

    @Inject
    RentService rentService;

    @Inject
    VMachineService vmService;

    List<Client> clients = new ArrayList<>();
    List<RentDTO> rents = new ArrayList<>();
    List<VMachine> vms = new ArrayList<>();

    @PostConstruct
    public void init() {
        initClient();
        initVM();
        initRent();
    }

        public void dropAndCreateClient(){
        clientService.getRepo().getDatabase().getCollection("clients").drop();
        clientService.getRepo().getDatabase().createCollection("clients");
        clientService.getRepo().getDatabase().getCollection("clients").createIndex(
                new Document("username", 1),
                new IndexOptions().unique(true)
        );
    }

    public void dropAndCreateVMachine(){
        vmService.getRepo().getDatabase().getCollection("vMachines").drop();
        vmService.getRepo().getDatabase().createCollection("vMachines");
    }

    public void dropAndCreateRent(){
        rentService.getRepo().getDatabase().getCollection("rents").drop();
        rentService.getRepo().getDatabase().getCollection("clients").drop();
        rentService.getRepo().getDatabase().getCollection("vMachines").drop();
        rentService.getRepo().getDatabase().createCollection("vMachines");
        rentService.getRepo().getDatabase().createCollection("clients");
        rentService.getRepo().getDatabase().createCollection("rents");
    }

    public void initClient() {
        clients.add(new Client("Bart", "Fox", "Idontexist", "BFox@tul.com", new Premium()));
        clients.add(new Client("Michael", "Corrugated", "DON_IAS", "MCorrugated@ias.pas.p.lodz.pl", new Premium()));
        clients.add(new Client("Matthew", "Tar", "MTar", "MTar@TarVSCorrugated.com", new Premium()));
        clients.add(new Client("Martin", "Bricky", "Brickman", "IntelEnjoyer@whatisonpage4035.com", new Standard()));
        clients.add(new Client("Juan", "Escobar", "JEscobar", "JEscobar@colombianSnow.com", new Standard()));

        clients.forEach(clientService::createClient);
    }

    public void initVM() {
        vms.add(new AppleArch(4, "4GB"));
        vms.add(new AppleArch(24, "128GB"));
        vms.add(new x86(8, "8GB", "AMD"));
        vms.add(new x86(16, "32GB", "Intel"));
        vms.add(new x86(128, "256GB", "Other"));
        vms.add(new x86(128, "256GB", "Other"));

        vms.forEach(vmService::createVMachine);
    }

    public void initRent() {
        rents.add(new RentDTO(clients.get(0).getEntityId().getUuid(), vms.get(0).getEntityId().getUuid(), LocalDateTime.of(2024, 11, 21, 21, 37)));
        rents.add(new RentDTO(clients.get(0).getEntityId().getUuid(), vms.get(2).getEntityId().getUuid(), LocalDateTime.of(2024, 10, 26, 21, 37)));
        rents.add(new RentDTO(clients.get(3).getEntityId().getUuid(), vms.get(3).getEntityId().getUuid(), LocalDateTime.of(2023, 10, 26, 21, 37)));
        rents.add(new RentDTO(clients.get(4).getEntityId().getUuid(), vms.get(1).getEntityId().getUuid(), LocalDateTime.of(2023, 11, 11, 11, 11)));
        rents.add(new RentDTO(clients.get(1).getEntityId().getUuid(), vms.get(4).getEntityId().getUuid(), LocalDateTime.of(2011, 11, 11, 11, 11)));

        rents.forEach(rentService::createRent);
    }
}

