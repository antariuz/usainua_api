package avada.media.usainua_api.model;

import avada.media.usainua_api.model.common.MappedEntity;
import avada.media.usainua_api.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Product extends MappedEntity {

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<User> user = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_mall_id")
    private ShoppingMall shoppingMall;
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<Category> categories = new HashSet<>();
    private String name;
    private Double price;
    private String url;
    @Lob
    private String description;
    private String image;

    public enum Category {
        TOP,
        SHOES,
        CLOTHES,
        ELECTRONIC,
        GAMING
    }

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/products/" + getId() + "/" + image;
    }

}
