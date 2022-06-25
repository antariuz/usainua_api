package avada.media.usainua_admin.service;

import avada.media.usainua_admin.model.order.Order;

public interface OrderService {

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    Iterable<Order> getAllOrders();

    Order getOrderById(Long id);

}
