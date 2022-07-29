package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.ShoppingMall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingMallRepo extends JpaRepository<ShoppingMall, Long> {
}
