package pl.lodz.p.mvc.model.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.mvc.model.MongoUUID;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class Client extends User{
    private ClientType clientType;
    private int currentRents;

    @Override
    public String toString() {
        return super.toString() + "::Client{" +
                "clientType=" + clientType +
                ", currentRents=" + currentRents +
                '}';
    }

    public Client(String firstName,
                  String surname,
                  String username,
                  String emailAddress,
                  ClientType clientType) {
        super(new MongoUUID(UUID.randomUUID()), firstName, username, surname, emailAddress, Role.CLIENT, true);
        this.clientType = clientType;
        this.currentRents = 0;
    }

    public Client(MongoUUID userId,
                  String firstName,
                  String username,
                  String surname,
                  String emailAddress,
                  Role role,
                  boolean active,
                  ClientType clientType,
                  int currentRents) {
        super(userId, firstName, username, surname, emailAddress, role, active);
        this.clientType = clientType;
        this.currentRents = currentRents;
    }
}
