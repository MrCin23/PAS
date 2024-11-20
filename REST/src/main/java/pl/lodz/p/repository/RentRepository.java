package pl.lodz.p.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import pl.lodz.p.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RentRepository extends AbstractMongoRepository {
    private final MongoCollection<Rent> rents;
    private final MongoCollection<VMachine> vMachines;
    private final MongoCollection<Client> clients;

    public RentRepository() {
        super.initDbConnection();
        MongoIterable<String> list = this.getDatabase().listCollectionNames();
        for (String name : list) {
            if (name.equals("rents")) {
                this.getDatabase().getCollection(name).drop();
                break;
            }
        }
        this.getDatabase().createCollection("rents");

        this.rents = this.getDatabase().getCollection("rents", Rent.class);
        this.vMachines = this.getDatabase().getCollection("vMachines", VMachine.class);
        this.clients = this.getDatabase().getCollection("clients", Client.class);
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void endRent(MongoUUID uuid, LocalDateTime endTime){
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();

            Bson filter1 = Filters.eq("_id", uuid.getUuid());
            Rent rent = rents.find(filter1).first();
            if(rent == null){
                throw new RuntimeException("Rent not found");
            } else if(rent.getEndTime() == null) {
                rent.endRent(endTime);
                Bson update1 = Updates.set("endTime", rent.getEndTime());
                rents.updateOne(session, filter1, update1);
                update1 = Updates.set("rentCost", rent.getRentCost());
                rents.updateOne(session, filter1, update1);
            }

            if(endTime.isBefore(rent.getBeginTime())){
                throw new RuntimeException("Rent cannot be ended before it has even begun! Aborting transaction");
            }

            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
            Bson update = Updates.inc("isRented", -1);
            vMachines.updateOne(session, filter, update);

            Bson filter2 = Filters.eq("_id", rent.getClient().getEntityId().getUuid().toString());
            Bson update2 = Updates.inc("currentRents", -1);
            vMachines.updateOne(session, filter2, update2);

            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public void update(long id, Map<String, Object> fieldsToUpdate) {

    }

    public void add(Rent rent) {
        ClientSession session = getMongoClient().startSession();
        Client client;
        try {
            session.startTransaction();
            Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
            Bson updateClientFilter = Updates.inc("currentRents", 1);
            clients.updateOne(session, clientFilter, updateClientFilter);
            Bson currentRentsFilter = Filters.lt("currentRents", rent.getClient().getClientType().getMaxRentedMachines());
            client = clients.find(Filters.and(clientFilter, currentRentsFilter)).first();
            if(client == null){
                throw new Exception("");
            }
            if(!client.isActive()){
                throw new RuntimeException("Client is not active");
                //TODO zmiana żeby przy tych dwóch wyjątkach nastąpiło wyrzucenie tego wyjątku
            }
            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
            Bson update = Updates.inc("isRented", 1);
            vMachines.updateOne(session, filter, update);
            rents.insertOne(rent);
            session.commitTransaction();
        } catch (Exception ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }

    }

    public long size(boolean active) {

        return 0;
    }

    public long size() {
        return rents.find().into(new ArrayList<>()).size();
    }

    public List<Rent> getRents(boolean active) {
        return rents.find().into(new ArrayList<>());
    }

    public List<Rent> getClientRents(MongoUUID clientId, boolean active) {
        Bson filter1 = Filters.eq("client._id", clientId.getUuid());
        Bson filter2;
        if(active){
            filter2 = Filters.eq("endTime", null);
        } else {
            filter2 = Filters.ne("endTime", null);
        }
        Bson filter3 = Filters.and(filter1, filter2);
        return rents.find(filter3).into(new ArrayList<>());
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

    public List<Rent> getRents() {
        return rents.find().into(new ArrayList<>());
    }

    public Rent getRentByID(MongoUUID uuid) {
        Bson filter = Filters.eq("_id", uuid.getUuid());
        return rents.find(filter).first();
    }

    public List<Rent> findBy(String field, Object value) {
        return rents.find(Filters.eq(field, value)).into(new ArrayList<>());
    }

    public List<Rent> findByNegation(String field, Object value) {
        return rents.find(Filters.ne(field, value)).into(new ArrayList<>());
    }
}
