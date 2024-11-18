package pl.lodz.p.model;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;


@Setter
@Getter
public class Client extends AbstractEntityMgd {
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("emailAddress")
    private String emailAddress;
    @BsonProperty("clientType")
    private ClientType clientType;
    @BsonProperty("currentRents")
    private int currentRents;

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", clientType=" + clientType +
                ", currentRents=" + currentRents +
                '}';
    }

    public Client(String firstName, String surname, String emailAddress, ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()));
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = 0;
    }

    @BsonCreator
    public Client(@BsonProperty("_id") MongoUUID clientID, @BsonProperty("firstName") String firstName,
                  @BsonProperty("surname") String surname, @BsonProperty("emailAddress") String emailAddress,
                  @BsonProperty("clientType") ClientType clientType, @BsonProperty("currentRents") int currentRents) {

        super(clientID);
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = currentRents;
    }
}
