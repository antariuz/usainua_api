package avada.media.usainua_api.repository;

import avada.media.usainua_api.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    void deleteOrderById(Long id);

    Page<Order> findAllByUserId(Long id, Pageable pageable);

    Order findOrderByIdAndUserId(Long orderId, Long userId);

}
