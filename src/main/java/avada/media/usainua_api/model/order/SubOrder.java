package avada.media.usainua_api.model.order;

import avada.media.usainua_api.model.common.MappedEntity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class SubOrder extends MappedEntity {

    private String url;
    @Column(name = "quantity")
    private Integer qty;
    private Double price;
    @Column(name = "estimate_weight")
    private Double estimateWeight;

    @ElementCollection(targetClass = AdditionalServices.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "suborder_additional_service", joinColumns = @JoinColumn(name = "suborder_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "additional_services")
    private Set<AdditionalServices> additionalServices = new HashSet<>();
    @Lob
    private String description;
    @OneToOne
    private InvoiceFile invoiceFile;
    @Column(name = "tracking_number")
    private String trackingNumber;

    public enum AdditionalServices {
        TAKE_PHOTO,
        ADDITIONAL_PACKAGE,
        ON_OFF_CHECK
    }

}
