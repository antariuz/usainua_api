package avada.media.usainua_admin.config;

import avada.media.usainua_admin.model.delivery.Country;
import avada.media.usainua_admin.model.user.PersonalData;
import avada.media.usainua_admin.model.user.User;
import avada.media.usainua_admin.repository.CountryRepository;
import avada.media.usainua_admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Init {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final CountryRepository countryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initUserTable() throws ParseException {
        if (!userService.getAllUsers().iterator().hasNext()) {
            User user1 = new User();
            user1.setEmail("bob@gmail.com");
            user1.setPassword(encoder.encode("bob"));
            user1.setBalance(0.00d);
            PersonalData personalData = new PersonalData();
            personalData.setFirstName("Bob");
            personalData.setLastName("Шишкин");
            personalData.setGender(PersonalData.Gender.MALE);
            personalData.setBirthDate(LocalDate.of(1800, 12, 13));
            personalData.setMobilePhoneNumber("+380675555555");
            user1.setPersonalData(personalData);

            User user2 = new User();
            user2.setEmail("todd@gmail.com");
            user2.setPassword(encoder.encode("todd"));
            user2.setBalance(1.00d);
            PersonalData personalData2 = new PersonalData();
            personalData2.setFirstName("Todd");
            personalData2.setLastName("Jackson");
            personalData2.setGender(PersonalData.Gender.FEMALE);
            personalData2.setBirthDate(LocalDate.of(2020, 1, 31));
            personalData2.setMobilePhoneNumber("+380676666666");
            user2.setPersonalData(personalData2);

            userService.createUser(user1);
            userService.createUser(user2);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initCountryTable() {
        if (countryRepository.findAll().isEmpty()) {
            for (String countryCode : Locale.getISOCountries()) {
                Locale obj = new Locale("", countryCode);
                Country country = new Country();
                country.setName(obj.getDisplayName(Locale.ENGLISH));
                countryRepository.save(country);
            }
        }
    }

}
