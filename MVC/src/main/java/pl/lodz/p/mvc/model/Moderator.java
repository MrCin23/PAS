package pl.lodz.p.mvc.model;


import java.util.UUID;


public class Moderator extends ClientType {

    public Moderator() {
        super(new MongoUUID(UUID.randomUUID()), 5, "Moderator");
    }

    @Override
    public String toString() {
        return "Moderator " + this.getClass().getSimpleName();
    }
}
