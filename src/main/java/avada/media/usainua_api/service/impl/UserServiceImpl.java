package avada.media.usainua_api.service.impl;

import avada.media.usainua_api.model.user.PersonalData;
import avada.media.usainua_api.model.user.Role;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.RoleRepo;
import avada.media.usainua_api.repository.UserRepo;
import avada.media.usainua_api.service.PersonalDataService;
import avada.media.usainua_api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final PersonalDataService personalDataService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final String generatedPassword = String.format("%04d", new Random().nextInt(10000));

    private final Pattern EMAIL_REGEX_RFC822 =
            Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
                    Pattern.CASE_INSENSITIVE);

    @Override
    public HttpStatus sendEmailConfirmationCode(String email) {
        if (EMAIL_REGEX_RFC822.matcher(email).matches()) {
            User user = new User();
            if (getUserByEmail(email).isPresent()) {
                user = getUserByEmail(email).get();
                user.setPassword(passwordEncoder.encode(generatedPassword));
                saveUser(user);
            } else {
                user.setEmail(email);
                user.getRoles().add(getRoleByName("ROLE_USER"));
                user.setPassword(passwordEncoder.encode(generatedPassword));
                PersonalData personalData = new PersonalData(
                        user,
                        0d,
                        "",
                        "",
                        null,
                        null,
                        ""
                );
                personalDataService.savePersonalData(personalData);
            }
            try {
                sendEmail(email, generatedPassword);
            } catch (MailSendException e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return HttpStatus.OK;
        } else return HttpStatus.BAD_REQUEST;
    }

    @Override
    public void randomPassword(UserDetails userDetails) {
        User user = getUserByEmail(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("Not found User with email: " + userDetails.getUsername())
        );
        user.setPassword(passwordEncoder.encode(String.format("%04d", new Random().nextInt(10000))));
        saveUser(user);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findRoleByName(name);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Not found User with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    private void sendEmail(String email, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("USAINUA confirmation code");
        msg.setText(String.format("Welcome to USAINUA ! \n Your confirmation code is %s", password));
        javaMailSender.send(msg);
    }

}
