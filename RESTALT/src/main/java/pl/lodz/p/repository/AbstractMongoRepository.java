package pl.lodz.p.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.lodz.p.DataInitializer;
import pl.lodz.p.codec.CodecProvider;
import pl.lodz.p.model.*;
import pl.lodz.p.model.user.Premium;
import pl.lodz.p.model.user.Standard;

@Getter
public abstract class AbstractMongoRepository implements AutoCloseable {
    private final ConnectionString connectionString = new ConnectionString(
        "mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
    private final MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "adminpassword".toCharArray());
    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true)
                    .register(Premium.class).register(Standard.class).register(x86.class).register(AppleArch.class)
                    .conventions(Conventions.DEFAULT_CONVENTIONS).build());
    private MongoClient mongoClient;
    private MongoDatabase database;


    @PostConstruct
    protected void initDbConnection() { //@Observes StartupEvent ev
//        ConnectionString connectionString = new ConnectionString(
//                "mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=replica_set_single");
//        MongoCredential credential = MongoCredential.createCredential(
//                "admin", "admin", "adminpassword".toCharArray());
//        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
//                PojoCodecProvider.builder().automatic(true)
//                        .register(Premium.class).register(Standard.class).register(x86.class).register(AppleArch.class)
//                        .conventions(Conventions.DEFAULT_CONVENTIONS).build());
//        MongoClient mongoClient;
//        MongoDatabase database;
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new CodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                        ))
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("vmrental");
    }

    @Override
    public void close() throws Exception {
        this.mongoClient.close();
    }
}