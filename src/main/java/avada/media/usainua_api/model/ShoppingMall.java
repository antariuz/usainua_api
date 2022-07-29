package avada.media.usainua_api.model;

import avada.media.usainua_api.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingMall extends MappedEntity {

    private String name;
    private String url;
    private String image;

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/" + image;
    }

}
