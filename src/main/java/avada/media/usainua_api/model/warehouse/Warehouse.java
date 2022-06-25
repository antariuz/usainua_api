package avada.media.usainua_admin.model.warehouse;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Warehouse extends MappedEntity {

    private String street;
    private String city;
    private String state;
    private String postcode;
    @Column(name = "mobile_phone_number")
    private String mobilePhone;
    private boolean main;

}
