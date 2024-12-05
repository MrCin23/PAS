package pl.lodz.p.mvc.model.user;

import pl.lodz.p.mvc.model.MongoUUID;

import java.util.UUID;

public class Standard extends ClientType{

    public Standard() {
        super(new MongoUUID(UUID.randomUUID()), 3, "Standard");
    }

    @Override
    public String toString() {
        return "Standard" + this.getClass().getSimpleName();
    }
}
