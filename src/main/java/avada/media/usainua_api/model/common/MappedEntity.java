package avada.media.usainua_admin.model.common;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class MappedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

}
