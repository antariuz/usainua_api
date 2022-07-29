package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.user.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonalDataRepo extends JpaRepository<PersonalData, Long> {
}
