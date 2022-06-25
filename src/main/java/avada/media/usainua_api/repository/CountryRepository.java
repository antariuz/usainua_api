package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.delivery.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
