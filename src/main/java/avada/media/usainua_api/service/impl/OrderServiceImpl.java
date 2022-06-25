package avada.media.usainua_admin.service.impl;

import avada.media.usainua_admin.model.order.Order;
import avada.media.usainua_admin.repository.OrderRepository;
import avada.media.usainua_admin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public void createOrder(Order order) {
        repository.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        repository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteOrderById(id);
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

}
