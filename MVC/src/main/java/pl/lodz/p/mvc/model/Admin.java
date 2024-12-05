package pl.lodz.p.mvc.model;


import java.util.UUID;

public class Admin extends ClientType{


    public Admin() {
        super(new MongoUUID(UUID.randomUUID()), 10, "Admin");
    }


    @Override
    public String toString() {
        return "Admin " + this.getClass().getSimpleName();
    }
}
