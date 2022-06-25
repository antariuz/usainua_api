package avada.media.usainua_admin.model.paymentTransaction;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "payment_transcation")
@Data
public class PaymentTransaction extends MappedEntity {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    private Double amount;
    private Type type;
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    public enum Type {
        ORDER_PAYMENT, OTHER
    }

    public enum PaymentMethod {
        BALANCE, EXTERNAL
    }

}
