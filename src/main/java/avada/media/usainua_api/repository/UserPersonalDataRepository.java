package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.user.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonalDataRepository extends JpaRepository<PersonalData, Long> {
}
