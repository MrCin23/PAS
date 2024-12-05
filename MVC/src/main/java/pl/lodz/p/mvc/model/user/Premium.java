package pl.lodz.p.mvc.model.user;

import pl.lodz.p.mvc.model.MongoUUID;

import java.util.UUID;

public class Premium extends ClientType {


    public Premium() {
        super(new MongoUUID(UUID.randomUUID()), 10, "Premium");
    }


    @Override
    public String toString() {
        return "Premium " + this.getClass().getSimpleName();
    }
}
