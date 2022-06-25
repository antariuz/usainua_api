package avada.media.usainua_admin.model.delivery.local;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class PostOfficeBranch extends MappedEntity {

    private Integer number;

}