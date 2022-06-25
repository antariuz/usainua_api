package avada.media.usainua_admin.model.user;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table
public class Role extends MappedEntity {

    private String name;

}
