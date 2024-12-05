package pl.lodz.p.mvc.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.mvc.model.AbstractEntityMgd;
import pl.lodz.p.mvc.model.MongoUUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class User extends AbstractEntityMgd {
    private String firstName;
    private String surname;
    private String username;
    private String emailAddress;
    private Role role;
    private boolean active;

    public User(MongoUUID userId,
                String firstName,
                String username,
                String surname,
                String emailAddress,
                Role role,
                boolean active) {
        super(userId);
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.role = role;
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}
