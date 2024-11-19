package pl.lodz.p.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class Client extends AbstractEntityMgd {
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("username")
    private String username;
    @BsonProperty("emailAddress")
    private String emailAddress;
    @BsonProperty("clientType")
    private ClientType clientType;
    @BsonProperty("currentRents")
    private int currentRents;
    @BsonProperty("active")
    private boolean active;

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", clientType=" + clientType +
                ", currentRents=" + currentRents +
                ", active=" + active +
                '}';
    }

    public Client(String firstName, String surname, String username, String emailAddress, ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()));
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = 0;
        this.active = true;
    }

    @BsonCreator
    public Client(@BsonProperty("_id") MongoUUID clientID, @BsonProperty("firstName") String firstName, @BsonProperty("username") String username,
                  @BsonProperty("surname") String surname, @BsonProperty("emailAddress") String emailAddress,
                  @BsonProperty("clientType") ClientType clientType, @BsonProperty("currentRents") int currentRents, @BsonProperty("active") boolean active) {

        super(clientID);
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.clientType = clientType;
        this.currentRents = currentRents;
        this.active = active;
    }
}
