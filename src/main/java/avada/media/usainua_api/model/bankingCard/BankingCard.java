package avada.media.usainua_admin.model.bankingCard;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "banking_card")
@Data
public class BankingCard extends MappedEntity {

    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiry_month")
    private int expiryMonth;
    @Column(name = "expiry_year")
    private int expiryYear;
    private Integer cvv;

}
