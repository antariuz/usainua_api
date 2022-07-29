package avada.media.usainua_api.model.delivery;

import avada.media.usainua_api.model.common.MappedEntity;
import avada.media.usainua_api.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table
@Data
public class ShippingAddress extends MappedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    private String title;
    private Country country;
    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;
    private String region;
    private String city;
    @Column(name = "post_branch")
    private String postBranch;
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "apartment_number")
    private Integer apartmentNumber;
    private boolean main;

    public enum Country {
        AFGHANISTAN,
        CHINA,
        RUSSIAN_FEDERATION,
        UKRAINE,
        UNITED_ARAB_EMIRATES,
        UNITED_KINGDOM,
        UNITED_STATES
    }

    public enum DeliveryType {
        TO_POST_OFFICE_BRANCH,
        TO_LOCAL_ADDRESS
    }

}
