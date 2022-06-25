package avada.media.usainua_admin.model.user;

import avada.media.usainua_admin.model.common.MappedEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "personal_data")
@Data
public class PersonalData extends MappedEntity {

    private String firstName;
    private String lastName;
    @Column(name = "birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    public enum Gender {
        MALE, FEMALE
    }

}
