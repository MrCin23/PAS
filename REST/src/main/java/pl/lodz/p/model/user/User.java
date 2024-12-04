package pl.lodz.p.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.lodz.p.model.AbstractEntityMgd;
import pl.lodz.p.model.MongoUUID;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator("User")
public abstract class User extends AbstractEntityMgd {
    @BsonProperty("firstName")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 32)
    private String firstName;
    @BsonProperty("surname")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 32)
    private String surname;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20)
    @BsonProperty("username")
    private String username;
    @BsonProperty("emailAddress")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email address has to be valid")
    private String emailAddress;
    @BsonProperty("role")
    private Role role;
    @BsonProperty("active")
    private boolean active;

    @BsonCreator
    public User(@BsonProperty("_id") MongoUUID userId,
                @BsonProperty("firstName") String firstName,
                @BsonProperty("username") String username,
                @BsonProperty("surname") String surname,
                @BsonProperty("emailAddress") String emailAddress,
                @BsonProperty("role") Role role,
                @BsonProperty("active") boolean active) {
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
