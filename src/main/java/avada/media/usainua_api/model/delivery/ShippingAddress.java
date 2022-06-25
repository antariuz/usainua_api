package avada.media.usainua_admin.model.delivery;

import avada.media.usainua_admin.model.common.MappedEntity;
import avada.media.usainua_admin.model.delivery.local.City;
import avada.media.usainua_admin.model.delivery.local.PostOfficeBranch;
import avada.media.usainua_admin.model.delivery.local.Street;
import avada.media.usainua_admin.model.delivery.local.Region;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class ShippingAddress extends MappedEntity {

    @Column(name = "address_label")
    private String addressLabel;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Country> countries = new HashSet<>();
    @Column(name = "local_delivery_type")
    @Enumerated(EnumType.STRING)
    private LocalDeliveryType localDeliveryType;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "region_id")
    private Region region;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "city_id")
    private City city;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_office_branch_id")
    private PostOfficeBranch postOfficeBranch;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "street_id")
    private Street street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "apartment_number")
    private Integer apartmentNumber;
    public enum LocalDeliveryType {
        TO_POST_OFFICE_BRANCH, TO_LOCAL_ADDRESS
    }

}
