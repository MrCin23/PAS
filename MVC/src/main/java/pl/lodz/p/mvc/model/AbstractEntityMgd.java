package pl.lodz.p.mvc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractEntityMgd implements Serializable {
    private MongoUUID entityId;

    public AbstractEntityMgd(MongoUUID entityId) {
        this.entityId = entityId;
    }
}