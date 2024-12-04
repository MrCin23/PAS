package pl.lodz.p.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@BsonDiscriminator(value="admin", key="_clazz")
public class Admin extends ClientType{


    public Admin() {
        super(new MongoUUID(UUID.randomUUID()), 10, "Admin");
    }


    @Override
    public String toString() {
        return "Admin " + this.getClass().getSimpleName();
    }
}
