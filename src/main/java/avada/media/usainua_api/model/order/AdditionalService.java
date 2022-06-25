package avada.media.usainua_admin.model.order;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addtional_services")
@Data
public class AdditionalService extends MappedEntity {

    private String name;

}
