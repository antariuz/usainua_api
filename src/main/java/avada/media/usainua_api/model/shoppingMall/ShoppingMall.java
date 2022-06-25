package avada.media.usainua_admin.model.shoppingMall;

import avada.media.usainua_admin.model.common.MappedEntity;
import avada.media.usainua_admin.model.product.Product;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class ShoppingMall extends MappedEntity {

    private String name;
    private String image;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/shoppingMalls/" + getId() + "/" + image;
    }

}
