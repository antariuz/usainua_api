package avada.media.usainua_admin.model.order;

import avada.media.usainua_admin.model.common.MappedEntity;
import avada.media.usainua_admin.model.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class SubOrder extends MappedEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Column(name = "additional_services")
    private Set<AdditionalService> additionalServices = new HashSet<>();
    @Lob
    private String description;
    @Column(name = "quantity")
    private Integer qty;
    private Double weight;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_file_id")
    private InvoiceFile invoiceFile;
    @Column(name = "tracking_number")
    private String trackingNumber;

}
