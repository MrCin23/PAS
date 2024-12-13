//package pl.lodz.p.repository;
//
//import com.mongodb.MongoCommandException;
//import com.mongodb.client.ClientSession;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoIterable;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.Updates;
//import jakarta.enterprise.context.ApplicationScoped;
//import org.bson.conversions.Bson;
//import pl.lodz.p.model.*;
//import pl.lodz.p.model.user.Client;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@ApplicationScoped
//public class RentRepository extends AbstractMongoRepository {
//    private final MongoCollection<Rent> rents;
//    private final MongoCollection<VMachine> vMachines;
//    private final MongoCollection<Client> clients;
//
//    public RentRepository() {
//        super.initDbConnection();
//        MongoIterable<String> list = this.getDatabase().listCollectionNames();
//        for (String name : list) {
//            if (name.equals("rents")) {
//                this.getDatabase().getCollection(name).drop();
//                break;
//            }
//        }
//        this.getDatabase().createCollection("rents");
//
//        this.rents = this.getDatabase().getCollection("rents", Rent.class);
//        this.vMachines = this.getDatabase().getCollection("vMachines", VMachine.class);
//        this.clients = this.getDatabase().getCollection("clients", Client.class);
//    }
//
//    public void endRent(MongoUUID uuid, LocalDateTime endTime){
//        ClientSession session = getMongoClient().startSession();
//        try {
//            session.startTransaction();
//
//            Bson filter1 = Filters.eq("_id", uuid.getUuid());
//            Rent rent = rents.find(filter1).first();
//            if(rent == null){
//                throw new RuntimeException("Rent not found");
//            } else if(rent.getEndTime() == null) {
//                rent.endRent(endTime);
//                Bson update1 = Updates.set("endTime", rent.getEndTime());
//                rents.updateOne(session, filter1, update1);
//                update1 = Updates.set("rentCost", rent.getRentCost());
//                rents.updateOne(session, filter1, update1);
//            }
//
//            if(endTime.isBefore(rent.getBeginTime())){
//                throw new RuntimeException("Rent cannot be ended before it has even begun! Aborting transaction");
//            }
//
//            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
//            Bson update = Updates.inc("isRented", -1);
//            vMachines.updateOne(session, filter, update);
//
//            Bson filter2 = Filters.eq("_id", rent.getClient().getEntityId().getUuid().toString());
//            Bson update2 = Updates.inc("currentRents", -1);
//            vMachines.updateOne(session, filter2, update2);
//
//            session.commitTransaction();
//        } catch (MongoCommandException ex) {
//            session.abortTransaction();
//        } finally {
//            session.close();
//        }
//    }
//
//    public void update(long id, Map<String, Object> fieldsToUpdate) {
//
//    }
//
//    public void add(Rent rent) {
//        ClientSession session = getMongoClient().startSession();
//        Client client;
//        try {
//            session.startTransaction();
////            VMachine vm = getVMachineById(rent.getVMachine().getEntityId().getUuid());
////            if(vm.isRented()>0){
////                throw new RuntimeException("I really shouldnt have to do this");
////            }
//            Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
//            Bson updateClientFilter = Updates.inc("currentRents", 1);
//            clients.updateOne(session, clientFilter, updateClientFilter);
//            Bson currentRentsFilter = Filters.lt("currentRents", rent.getClient().getClientType().getMaxRentedMachines());
//            client = clients.find(Filters.and(clientFilter, currentRentsFilter)).first();
//            if(client == null || !client.isActive()){
//                throw new RuntimeException("Client doesnt exist or is not active");
//            }
//            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
//            Bson update = Updates.inc("isRented", 1);
//            vMachines.updateOne(session, filter, update);
//            rents.insertOne(rent);
//            session.commitTransaction();
//        } catch (Exception ex) {
//            session.abortTransaction();
//        } finally {
//            session.close();
//        }
//
//    }
//
//    public long size(boolean active) {
//
//        return 0;
//    }
//
//    public long size() {
//        return rents.find().into(new ArrayList<>()).size();
//    }
//
//    public List<Rent> getRents(boolean active) {
//        return rents.find().into(new ArrayList<>());
//    }
//
//    public List<Rent> getClientRents(MongoUUID clientId, boolean active) {
//        Bson filter1 = Filters.eq("client._id", clientId.getUuid());
//        Bson filter2;
//        if(active){
//            filter2 = Filters.eq("endTime", null);
//        } else {
//            filter2 = Filters.ne("endTime", null);
//        }
//        Bson filter3 = Filters.and(filter1, filter2);
//        return rents.find(filter3).into(new ArrayList<>());
//    }
//
//    public List<Rent> getVMachineRents(MongoUUID vmId, boolean active) {
//        Bson filter1 = Filters.eq("vMachine._id", vmId.getUuid().toString());
//        Bson filter2;
//        if(active){
//            filter2 = Filters.eq("endTime", null);
//        } else {
//            filter2 = Filters.ne("endTime", null);
//        }
//        Bson filter3 = Filters.and(filter1, filter2);
//
//        return rents.find(filter3).into(new ArrayList<>());
//    }
//
//    public List<Rent> getRents() {
//        return rents.find().into(new ArrayList<>());
//    }
//
//    public Rent getRentByID(MongoUUID uuid) {
//        Bson filter = Filters.eq("_id", uuid.getUuid());
//        return rents.find(filter).first();
//    }
//
//    public List<Rent> findBy(String field, Object value) {
//        return rents.find(Filters.eq(field, value)).into(new ArrayList<>());
//    }
//
//    public List<Rent> findByNegation(String field, Object value) {
//        return rents.find(Filters.ne(field, value)).into(new ArrayList<>());
//    }
//
//    public void remove(MongoUUID uuid) {
//        ClientSession session = getMongoClient().startSession();
//        Client client;
//        try {
//            Bson idFilter = Filters.eq("_id", uuid.getUuid());
//            Bson activeFilter = Filters.eq("endTime", null);
//            Bson filter = Filters.and(idFilter, activeFilter);
//            Rent rentToDelete = rents.find(filter).first();
//            if (rentToDelete != null) {
//                Bson clientUpdate = Updates.inc("currentRents", -1);
//                Bson vMachineUpdate = Updates.inc("isRented", -1);
//                clients.updateOne(session, Filters.eq("_id", rentToDelete.getClient().getEntityId().getUuid()), clientUpdate);
//                vMachines.updateOne(session, Filters.eq("_id", rentToDelete.getVMachine().getEntityId().getUuid().toString()), vMachineUpdate);
//                rents.deleteOne(filter);
//            } else {
//                throw new RuntimeException("Rent do not exist");
//            }
//        }
//        catch (MongoCommandException ex) {
//            session.abortTransaction();
//        }
//        finally {
//            session.close();
//        }
//    }
////
////    private VMachine getVMachineById(UUID vMachineId) {
////        RestTemplate restTemplate = new RestTemplate();
////        String url = "http://localhost:8081/REST/api/vmachine/" + vMachineId;
////        try {
////            return restTemplate.getForObject(url, VMachine.class);
////        } catch (Exception e) {
////            throw new RuntimeException("Request GET http://localhost:8081/REST/api/vmachine/" + vMachineId + " failed: " + e);
////        }
////    }
//}

package pl.lodz.p.repository;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.conversions.Bson;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.model.Rent;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.model.user.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RentRepository extends AbstractMongoRepository {

    private static final String COLLECTION_NAME = "rents";
    private MongoCollection<Rent> rents;
    private MongoCollection<VMachine> vMachines;
    private MongoCollection<Client> clients;

    @PostConstruct
    @Override
    protected void initDbConnection() {
        super.initDbConnection();

        // Drop existing collection (for development/testing purposes)
        if (this.getDatabase().listCollectionNames().into(new ArrayList<>()).contains(COLLECTION_NAME)) {
            this.getDatabase().getCollection(COLLECTION_NAME).drop();
        }
        this.getDatabase().createCollection(COLLECTION_NAME);

        this.rents = this.getCollection(COLLECTION_NAME, Rent.class);
        this.vMachines = this.getCollection("vMachines", VMachine.class);
        this.clients = this.getCollection("clients", Client.class);
    }

    public void add(Rent rent) {
        try (ClientSession session = getMongoClient().startSession()) {
            session.startTransaction();
            try {
                // Validate client
                Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
                Bson updateClientRents = Updates.inc("currentRents", 1);
                clients.updateOne(session, clientFilter, updateClientRents);
                Bson currentRentsFilter = Filters.lt("currentRents", rent.getClient().getClientType().getMaxRentedMachines());
                Client client = clients.find(session, Filters.and(clientFilter, currentRentsFilter)).first();

                if (client == null || !client.isActive()) {
                    throw new RuntimeException("Client does not exist or is not active");
                }

                // Validate VMachine
                Bson vMachineFilter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid());
                Bson updateVMachineRented = Updates.inc("isRented", 1);
                vMachines.updateOne(session, vMachineFilter, updateVMachineRented);

                // Insert rent
                rents.insertOne(session, rent);

                session.commitTransaction();
            } catch (Exception ex) {
                session.abortTransaction();
                throw ex;
            }
        }
    }

    public void endRent(MongoUUID uuid, LocalDateTime endTime) {
        try (ClientSession session = getMongoClient().startSession()) {
            session.startTransaction();
            try {
                Bson rentFilter = Filters.eq("_id", uuid.getUuid());
                Rent rent = rents.find(session, rentFilter).first();

                if (rent == null) {
                    throw new RuntimeException("Rent not found");
                } else if (rent.getEndTime() != null) {
                    throw new RuntimeException("Rent is already ended");
                } else if (endTime.isBefore(rent.getBeginTime())) {
                    throw new RuntimeException("Rent cannot end before it starts");
                }

                // Update rent
                rent.endRent(endTime);
                rents.updateOne(session, rentFilter, Updates.combine(
                        Updates.set("endTime", rent.getEndTime()),
                        Updates.set("rentCost", rent.getRentCost())
                ));

                // Update VMachine
                Bson vMachineFilter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid());
                vMachines.updateOne(session, vMachineFilter, Updates.inc("isRented", -1));

                // Update Client
                Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
                clients.updateOne(session, clientFilter, Updates.inc("currentRents", -1));

                session.commitTransaction();
            } catch (Exception ex) {
                session.abortTransaction();
                throw ex;
            }
        }
    }

    public void remove(MongoUUID uuid) {
        try (ClientSession session = getMongoClient().startSession()) {
            session.startTransaction();
            try {
                Bson rentFilter = Filters.eq("_id", uuid.getUuid());
                Rent rent = rents.find(session, rentFilter).first();

                if (rent == null || rent.getEndTime() != null) {
                    throw new RuntimeException("Active rent not found");
                }

                // Update Client
                Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
                clients.updateOne(session, clientFilter, Updates.inc("currentRents", -1));

                // Update VMachine
                Bson vMachineFilter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid());
                vMachines.updateOne(session, vMachineFilter, Updates.inc("isRented", -1));

                // Remove Rent
                rents.deleteOne(session, rentFilter);

                session.commitTransaction();
            } catch (Exception ex) {
                session.abortTransaction();
                throw ex;
            }
        }
    }

    public List<Rent> getActiveRents() {
        return rents.find(Filters.eq("endTime", null)).into(new ArrayList<>());
    }

        public List<Rent> getRents() {
        return rents.find().into(new ArrayList<>());
    }

    public List<Rent> getClientRents(MongoUUID clientId, boolean active) {
        Bson clientFilter = Filters.eq("client._id", clientId.getUuid());
        Bson activeFilter = active ? Filters.eq("endTime", null) : Filters.ne("endTime", null);
        return rents.find(Filters.and(clientFilter, activeFilter)).into(new ArrayList<>());
    }

    public Rent getRentByID(MongoUUID uuid) {
        return rents.find(Filters.eq("_id", uuid.getUuid())).first();
    }

        public List<Rent> findBy(String field, Object value) {
        return rents.find(Filters.eq(field, value)).into(new ArrayList<>());
    }

    public List<Rent> findByNegation(String field, Object value) {
        return rents.find(Filters.ne(field, value)).into(new ArrayList<>());
    }

        public List<Rent> getVMachineRents(MongoUUID vmId, boolean active) {
        Bson filter1 = Filters.eq("vMachine._id", vmId.getUuid().toString());
        Bson filter2;
        if(active){
            filter2 = Filters.eq("endTime", null);
        } else {
            filter2 = Filters.ne("endTime", null);
        }
        Bson filter3 = Filters.and(filter1, filter2);

        return rents.find(filter3).into(new ArrayList<>());
    }
}
