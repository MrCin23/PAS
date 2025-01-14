package pl.lodz.p.service;

import pl.lodz.p.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IUserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUser(UUID uuid);

    void updateUser(UUID uuid, Map<String, Object> fieldsToUpdate);

    void activateUser(UUID uuid);

    void deactivateUser(UUID uuid);

    //void deleteUser(UUID uuid);

    User getUserByUsername(String username);

    List<User> getUsersByUsername(String username);
}
