package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.BankingCard;
import avada.media.usainua_api.model.delivery.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepo extends JpaRepository<ShippingAddress, Long> {
    ShippingAddress findShippingAddressByMainEquals(boolean main);

    List<ShippingAddress> findByUserId(Long id);
}
