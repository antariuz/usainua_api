package avada.media.usainua_api.model.order;

import avada.media.usainua_api.model.common.MappedEntity;
import avada.media.usainua_api.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "_order")
@Data
public class Order extends MappedEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;
    @Column(name = "delivery_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate deliveryDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "total_weight")
    private Double totalWeight;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "commission_price")
    private Double commissionPrice;
    @Column(name = "insurance_price")
    private Double insurancePrice;
    @Column(name = "clearance_price")
    private Double clearancePrice;
    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SubOrder> subOrders = new LinkedList<>();

    public enum OrderType {
        PURCHASE_AND_DELIVERY,
        ONLY_DELIVERY
    }

    public enum Status {
        CALCULATING_ORDER,
        WAITING_PAYMENT,
        PAID,
        AWAITING_DELIVERY_TO_WAREHOUSE,
        DELIVERED_TO_WAREHOUSE,
        SENT_TO_UKRAINE,
        DELIVERED_TO_UKRAINE,
        READY_TO_LOCAL_DELIVERY,
        ORDER_IS_DONE
    }

    public enum DeliveryType {
        AIR,
        SEA
    }

}
