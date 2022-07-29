package avada.media.usainua_api.service;

import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.Product;
import avada.media.usainua_api.model.order.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    void saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    Order getOrderByIdAndUserId(Long orderId, Long userId);

    PageResponse<Order> getOrdersByPageByUserId(Long id, int pageNumber, int pageSize, String sortBy, String sortDirection);

}
