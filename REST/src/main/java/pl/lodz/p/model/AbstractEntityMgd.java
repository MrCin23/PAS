package pl.lodz.p.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractEntityMgd implements Serializable {
    @BsonProperty("_id")
    @BsonId
    private final MongoUUID entityId;

    @BsonCreator
    public AbstractEntityMgd(@BsonProperty MongoUUID entityId) {
        this.entityId = entityId;
    }
}