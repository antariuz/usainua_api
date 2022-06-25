package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    void deleteUserById(Long id);

}
