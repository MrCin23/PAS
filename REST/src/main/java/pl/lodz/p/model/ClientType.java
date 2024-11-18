package pl.lodz.p.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
public abstract class ClientType extends AbstractEntityMgd {

    @BsonProperty("maxRentedMachines")
    protected int maxRentedMachines;

    @BsonProperty("name")
    protected String name;


    @BsonCreator
    public ClientType(@BsonProperty("_id") MongoUUID uuid,
                      @BsonProperty("maxRentedMachines") int maxRentedMachines,
                      @BsonProperty("name") String name){
        super(uuid);
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    public String toString() {
        return "Class: " + this.getClass().getSimpleName() + " " + this.getMaxRentedMachines() + " " + this.getName();
    }
}
