package pl.lodz.p.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import pl.lodz.p.model.Client;
import pl.lodz.p.model.MongoUUID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Repository
public class ClientRepository extends AbstractMongoRepository {
    private final String collectionName = "clients";
    private final MongoCollection<Client> clients;


    public ClientRepository() {
        super.initDbConnection();
        MongoIterable<String> list = this.getDatabase().listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                this.getDatabase().getCollection(name).drop();
                break;
            }
        }
//        Bson currentRentsType = Filters.type("currentRents", BsonType.INT32);
//        Bson currentRentsMin  = Filters.gte("currentRents", 0);
//        Bson currentRentsMax  = Filters.expr(Filters.lte("$currentRents", "$clientType.maxRentedMachines"));

        ValidationOptions validationOptions = new ValidationOptions().validator(
                        Document.parse("""
            {
                $jsonSchema: {
                    "bsonType": "object",
                    "required": [ "_id", "active", "clientType", "currentRents", "emailAddress", "firstName", "surname", "username" ],
                    "properties": {
                        "_id" : {
                        }
                        "active" : {
                            "bsonType": "bool"
                        }
                        "clientType" : {
                            "bsonType": "object"
                            "required": [ "_clazz", "maxRentedMachines", "name" ],
                            "properties": {
                                "_clazz" : {
                                    "bsonType" : "string"
                                }
                                "maxRentedMachines" : {
                                    "bsonType": "int"
                                }
                                "name" : {
                                    "bsonType": "string"
                                }
                            }
                        }
                        "currentRents" : {
                            "bsonType": "int",
                            "minimum" : 0,
                            "maximum" : 10
                        }
                        "emailAddress" : {
                            "bsonType": "string"
                        }
                        "firstName" : {
                            "bsonType": "string"
                        }
                        "surname" : {
                            "bsonType": "string"
                        }
                        "username" : {
                            "bsonType": "string"
                        }
                    }
                }
            }
        """))//.validator(Filters.and(currentRentsType, currentRentsMin, currentRentsMax))
                .validationAction (ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions() .validationOptions (validationOptions);
        this.getDatabase().createCollection(collectionName, createCollectionOptions);

        this.clients = this.getDatabase().getCollection(collectionName, Client.class);
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(MongoUUID uuid, Map<String, Object> fieldsToUpdate) {
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();
            Bson filter = Filters.eq("_id", uuid.getUuid());
            Bson update;
            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                if(Objects.equals(fieldName, "currentRents")){
                    if((int)fieldValue == 1) {
                        update = Updates.inc("currentRents", 1);
                    } else {
                        update = Updates.inc("currentRents", -1);
                    }
                } else {
                    update = Updates.set(fieldName,fieldValue);
                }
                clients.updateOne(session, filter, update);
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
            Bson filter = Filters.eq("_id", uuid.getUuid());
            Bson update;
            if(Objects.equals(field, "currentRents")){
                if((int)value == 1) {
                    update = Updates.inc("currentRents", 1);
                } else {
                    update = Updates.inc("currentRents", -1);
                }
            } else {
                update = Updates.set(field,value);
            }
            clients.updateOne(session, filter, update);
            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public void add(Client client) {
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();
            Bson filter = Filters.eq("username", client.getUsername());
            Client pom = clients.find(session, filter).first();
            if(pom != null) {
                throw new RuntimeException("This username is already used");
            }
            clients.insertOne(client);
            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public void remove(Client client) {
        Bson filter = Filters.eq("_id", client.getEntityId());
        Client deletedClient = clients.findOneAndDelete(filter);
    }

    public long size() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Client c").uniqueResult();
//            transaction.commit();
//            return count;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return 0;
    }

    public List<Client> getClients() {

        return clients.find().into(new ArrayList<>());
    }

    public Client getClientByID(MongoUUID uuid) {
        Bson filter = Filters.eq("_id", uuid.getUuid());
        return clients.find(filter).first();
    }

    public Client getClientByUsername(String username) {
        Bson filter = Filters.eq("username", username);
        return clients.find(filter).first();
    }

    public List<Client> getClientsByUsername(String username) {
        Bson filter = Filters.regex("username", ".*" + Pattern.quote(username) + ".*", "i"); // "i" for case-insensitive search
        return clients.find(filter).into(new ArrayList<>());
    }

}
