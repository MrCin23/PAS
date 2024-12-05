package pl.lodz.p.mvc.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractEntityMgd implements Serializable {
    private final MongoUUID entityId;

    public AbstractEntityMgd() {
        this.entityId = new MongoUUID(UUID.randomUUID());
    }

    public AbstractEntityMgd( MongoUUID entityId) {
        this.entityId = entityId;
    }
}