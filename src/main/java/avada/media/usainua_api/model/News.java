package avada.media.usainua_api.model;

import avada.media.usainua_api.model.common.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News extends MappedEntity {

    private String title;
    @Lob
    private String description;
    private String image;

    @Transient
    public String getImagePath() {
        if (image == null || getId() == null) return null;
        return "/uploaded/" + image;
    }

}
