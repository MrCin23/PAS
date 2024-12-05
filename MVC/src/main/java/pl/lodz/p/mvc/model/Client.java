package pl.lodz.p.mvc.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
//@Document(collection = "clients")
public class Client extends AbstractEntityMgd {
    private String firstName;
    private String surname;
    private String username;
    private String emailAddress;
    private ClientType clientType;
    private int currentRents;
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


    public Client(MongoUUID clientID, String firstName, String username,
                  String surname, String emailAddress,
                  ClientType clientType, int currentRents, boolean active) {

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
