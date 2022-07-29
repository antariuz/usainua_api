package avada.media.usainua_api.model.dto;

import avada.media.usainua_api.model.order.Order;
import avada.media.usainua_api.model.order.SubOrder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class OrderDTO {

    private Order.OrderType orderType;
    private Order.DeliveryType deliveryType;
    private List<SubOrder> subOrders = new LinkedList<>();

}
