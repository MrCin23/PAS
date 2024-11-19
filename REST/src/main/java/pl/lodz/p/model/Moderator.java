package pl.lodz.p.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@BsonDiscriminator(value="moderator", key="_clazz")
public class Moderator extends ClientType {

    public Moderator() {
        super(new MongoUUID(UUID.randomUUID()), 5, "Moderator");
    }

    @Override
    public String toString() {
        return "Moderator " + this.getClass().getSimpleName();
    }
}
