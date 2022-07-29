package avada.media.usainua_api.model;

import avada.media.usainua_api.model.common.MappedEntity;
import avada.media.usainua_api.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "banking_card")
@Data
public class BankingCard extends MappedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiry_month")
    private int expiryMonth;
    @Column(name = "expiry_year")
    private int expiryYear;
    private String cvv;
    private boolean main;

}
