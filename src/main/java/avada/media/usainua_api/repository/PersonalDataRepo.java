package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.user.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonalDataRepo extends JpaRepository<PersonalData, Long> {
}
