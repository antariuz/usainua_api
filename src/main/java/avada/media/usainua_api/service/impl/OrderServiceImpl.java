package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.config.AppConst;
import avada.media.usainua_api.model.PageResponse;
import avada.media.usainua_api.model.order.Order;
import avada.media.usainua_api.model.order.SubOrder;
import avada.media.usainua_api.repository.OrderRepo;
import avada.media.usainua_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    @Override
    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepo.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepo.deleteOrderById(id);
    }

    @Override
    public Order getOrderByIdAndUserId(Long orderId, Long userId) {
        return orderRepo.findOrderByIdAndUserId(orderId, userId);
    }

    @Transactional
    @Override
    public PageResponse<Order> getOrdersByPageByUserId(Long id, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Page<Order> newsList = orderRepo.findAllByUserId(id, PageRequest.of(pageNumber, pageSize,
                sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending()));
        return new PageResponse<>(
                newsList.getContent(),
                newsList.getNumber(),
                newsList.getSize(),
                newsList.getTotalElements(),
                newsList.getTotalPages(),
                newsList.isLast()
        );
    }
}
