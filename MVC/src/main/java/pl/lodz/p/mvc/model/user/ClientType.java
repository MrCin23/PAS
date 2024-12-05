package pl.lodz.p.mvc.model.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.lodz.p.mvc.model.AbstractEntityMgd;
import pl.lodz.p.mvc.model.MongoUUID;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "_clazz"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Standard.class, name = "standard"),
        @JsonSubTypes.Type(value = Premium.class, name = "admin")
})
public abstract class ClientType extends AbstractEntityMgd {

    protected int maxRentedMachines;

    protected String name;

    @BsonCreator
    public ClientType(MongoUUID uuid,
                      int maxRentedMachines,
                      String name){
        super(uuid);
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    public String toString() {
        return "Class: " + this.getClass().getSimpleName() + " " + this.getMaxRentedMachines() + " " + this.getName();
    }
}
