package pl.lodz.p.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.model.VMachine;
import pl.lodz.p.model.user.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class VMachineRepository extends AbstractMongoRepository {
    private static final String COLLECTION_NAME = "vMachines";
    private MongoCollection<VMachine> vMachines;

    @PostConstruct
    @Override
    protected void initDbConnection() {
        super.initDbConnection();

        if (this.getDatabase().listCollectionNames().into(new ArrayList<>()).contains(COLLECTION_NAME)) {
            this.getDatabase().getCollection(COLLECTION_NAME).drop();
        }

        ValidationOptions validationOptions = new ValidationOptions().validator(
                        Document.parse("""
            {
                $jsonSchema: {
                    "bsonType": "object",
                    "required": [ "_id", "_clazz", "CPUNumber", "ramSize", "isRented", "actualRentalPrice" ],
                    "properties": {
                        "_id" : {
                            "bsonType": "string",
                            "minLength": 36,
                            "maxLength": 36
                        }
                        "_clazz" : {
                            "bsonType": "string"
                        }
                        "CPUNumber" : {
                            "bsonType": "int"
                            "minimum" : 1
                        }
                        "ramSize" : {
                            "bsonType": "string"
                        }
                        "isRented" : {
                            "bsonType": "int",
                            "minimum" : 0,
                            "maximum" : 1,
                            "description": "must be 1 for rented and for available"
                        }
                        "actualRentalPrice" : {
                            "bsonType": "double"
                        }
                        "CPUManufacturer" : {
                            "bsonType": "string"
                        }
                    }
                }
            }
        """))
                .validationAction (ValidationAction.ERROR);
        this.getDatabase().createCollection(COLLECTION_NAME, new CreateCollectionOptions().validationOptions(validationOptions));
        this.vMachines = this.getCollection(COLLECTION_NAME, VMachine.class);
//        this.vMachines.createIndex(new Document("username", 1), new IndexOptions().unique(true));
    }

//    public VMachineRepository() {
//        super.initDbConnection();
//        MongoIterable<String> list = this.getDatabase().listCollectionNames();
//        for (String name : list) {
//            if (name.equals(collectionName)) {
//                this.getDatabase().getCollection(name).drop();
//                break;
//            }
//        }
//
//
//        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions() .validationOptions (validationOptions);
//        this.getDatabase().createCollection(collectionName, createCollectionOptions);
//
//        this.vMachines = this.getDatabase().getCollection(collectionName, VMachine.class);
//    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(MongoUUID uuid, Map<String, Object> fieldsToUpdate) {
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();
            Bson filter = Filters.eq("_id", uuid.getUuid().toString());
            Bson update;
            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                if(Objects.equals(fieldName, "isRented")){
                    if((int)fieldValue == 1) {
                        update = Updates.inc("isRented", 1);
                    } else {
                        update = Updates.inc("isRented", -1);
                    }
                } else {
                    update = Updates.set(fieldName,fieldValue);
                }
                vMachines.updateOne(session, filter, update);
            }
            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public void update(MongoUUID uuid, String field, Object value) {
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();
            Bson filter = Filters.eq("_id", uuid.getUuid().toString());
            Bson update;
            if(Objects.equals(field, "isRented")){
                if((int)value == 1) {
                    update = Updates.inc("isRented", 1);
                } else {
                    update = Updates.inc("isRented", -1);
                }
            } else {
                update = Updates.set(field,value);
            }
            vMachines.updateOne(session, filter, update);
            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public void add(VMachine vMachine) {
        vMachines.insertOne(vMachine);
    }

    public void remove(VMachine vMachine) {
        Bson filter = Filters.eq("_id", vMachine.getEntityId().getUuid().toString());
        VMachine deletedVMachine = vMachines.findOneAndDelete(filter);
    }

    public long size() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            Long count = (Long) session.createQuery("SELECT COUNT(vm) FROM VMachine vm").uniqueResult();
//            transaction.commit();
//            return count;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return 0;
    }

    public List<VMachine> getVMachines() {
        return vMachines.find().into(new ArrayList<>());
    }

    public VMachine getVMachineByID(MongoUUID uuid) {
        Bson filter = Filters.eq("_id", uuid.getUuid().toString());
        return vMachines.find(filter).first();
    }
}
