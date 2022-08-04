package avada.media.usainua_api.service;

import avada.media.usainua_api.model.user.Role;
import avada.media.usainua_api.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    HttpStatus sendEmailConfirmationCode(String email);

    void randomPassword(UserDetails userDetails);

    void saveUser(User user);

    Role getRoleByName(String name);

    Optional<User> getUserByEmail(String email);

}
