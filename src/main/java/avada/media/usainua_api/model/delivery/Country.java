package avada.media.usainua_admin.model.delivery;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Country extends MappedEntity {

    private String name;

}
