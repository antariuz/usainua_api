package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.order.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrderRepo extends JpaRepository<SubOrder, Long> {
}
