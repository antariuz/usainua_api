package avada.media.usainua_api.model.order;

import avada.media.usainua_api.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class InvoiceFile extends MappedEntity {

    private String name;

}
