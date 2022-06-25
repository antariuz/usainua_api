package avada.media.usainua_admin.model.product;

import avada.media.usainua_admin.model.common.MappedEntity;
import avada.media.usainua_admin.model.shoppingMall.ShoppingMall;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Product extends MappedEntity {

    private String name;
    private Double price;
    private String url;
    @Lob
    private String description;
    private String image;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Column(name = "shopping_malls")
    private Set<ShoppingMall> shoppingMalls = new HashSet<>();

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/products/" + getId() + "/" + image;
    }

}
