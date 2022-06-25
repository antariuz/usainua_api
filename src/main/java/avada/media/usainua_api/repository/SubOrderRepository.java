package avada.media.usainua_admin.repository;

import avada.media.usainua_admin.model.order.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, Long> {
}
