package avada.media.usainua_api.model;

import avada.media.usainua_api.model.common.MappedEntity;
import avada.media.usainua_api.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "payment_transcation")
@Data
public class PaymentTransaction extends MappedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public enum Type {
        ORDER_PAYMENT, OTHER
    }

    public enum PaymentMethod {
        BALANCE, EXTERNAL
    }

}
