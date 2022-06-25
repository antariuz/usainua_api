package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.shoppingMall.ShoppingMall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingMallRepository extends JpaRepository<ShoppingMall, Long> {
}
