package avada.media.usainua_admin.model.user;

import avada.media.usainua_admin.model.bankingCard.BankingCard;
import avada.media.usainua_admin.model.common.MappedEntity;
import avada.media.usainua_admin.model.delivery.ShippingAddress;
import avada.media.usainua_admin.model.paymentTransaction.PaymentTransaction;
import avada.media.usainua_admin.model.product.Product;
import avada.media.usainua_admin.model.warehouse.Warehouse;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "_user")
@Data
public class User extends MappedEntity {

    private String email;
    private String password;
    private Double balance;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personal_data_id")
    private PersonalData personalData;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<BankingCard> bankingCards = new LinkedList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<ShippingAddress> shippingAddresses = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Warehouse> warehouses = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<PaymentTransaction> paymentTransactions = new LinkedList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Product> wishlist = new LinkedList<>();

}
