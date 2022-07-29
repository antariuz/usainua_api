package avada.media.usainua_api.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class MappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @ApiModelProperty(hidden = true, position = 1)
    private Long id;

}
