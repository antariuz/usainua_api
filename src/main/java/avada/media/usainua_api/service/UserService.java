package avada.media.usainua_admin.service;

import avada.media.usainua_admin.model.user.User;

public interface UserService {

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    Iterable<User> getAllUsers();

    User getUserById(Long id);

}
