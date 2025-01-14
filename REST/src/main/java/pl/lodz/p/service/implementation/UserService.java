package pl.lodz.p.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.model.user.User;
import pl.lodz.p.model.MongoUUID;
import pl.lodz.p.model.user.User;
import pl.lodz.p.repository.UserRepository;
import pl.lodz.p.service.IUserService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repo;

    @Override
    public User createUser(User user) {
        if(repo.getUserByID(user.getEntityId()) == null) {
            repo.add(user);
            return user;
        }
        throw new RuntimeException("User with id " + user.getEntityId() + " already exists");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = repo.getUsers();
        if(users == null || users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return repo.getUsers();
    }

    @Override
    public User getUser(UUID uuid) {
        User user = repo.getUserByID(new MongoUUID(uuid));
        if(user == null) {
            throw new RuntimeException("User with id " + uuid + " does not exist");
        }
        return user;
    }

    @Override
    public void updateUser(UUID uuid, Map<String, Object> fieldsToUpdate) {
        if(repo.getUserByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("User with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), fieldsToUpdate);
    }

    @Override
    public void activateUser(UUID uuid) {
        if(repo.getUserByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("User with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), "active", true);
    }

    @Override
    public void deactivateUser(UUID uuid) {
        if(repo.getUserByID(new MongoUUID(uuid)) == null) {
            throw new RuntimeException("User with id " + uuid + " does not exist");
        }
        repo.update(new MongoUUID(uuid), "active", false);
    }

//    @Override
//    public void deleteUser(UUID uuid) {
//        repo.remove(repo.getUserByID(new MongoUUID(uuid)));
//    }

    @Override
    public User getUserByUsername(String username) {
        if(repo.getUserByUsername(username) == null) {
            throw new RuntimeException("User with username " + username + " does not exist");
        }
        return repo.getUserByUsername(username);
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        if(repo.getUsersByUsername(username) == null || repo.getUsersByUsername(username).isEmpty()) {
            throw new RuntimeException("No users with username " + username + " found");
        }
        return repo.getUsersByUsername(username);
    }
}
