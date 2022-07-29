package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.user.PersonalData;
import avada.media.usainua_api.model.user.Role;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.PersonalDataRepo;
import avada.media.usainua_api.repository.RoleRepo;
import avada.media.usainua_api.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private RoleRepo roleRepo;
    @MockBean
    private PersonalDataRepo personalDataRepo;
    @MockBean
    private JavaMailSender javaMailSender;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    void getEmailConfirmationWithExistingUser() throws Exception {
        String email = "tymur.foshch@gmail.com";
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(new User(email, "1234", new HashSet<>())));
        when(passwordEncoder.encode(anyString())).thenReturn("3210");
        when(userRepo.save(any(User.class))).thenReturn(new User());
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        mockMvc.perform(
                        get("/api/auth/send_code").param("email", email))
                .andExpect(status().isOk());
        verify(userRepo, times(2)).findByEmail(email);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepo, times(1)).save(any(User.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @WithMockUser
    void getEmailConfirmationWithWrongEmail() throws Exception {
        String email = "petrov@ gmail.com";
        mockMvc.perform(
                        get("/api/auth/send_code").param("email", email))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "")
    void getEmailConfirmationWithNewUser() throws Exception {
        String email = "tymur.foshch@gmail.com";
        given(userRepo.findByEmail(anyString())).willReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("3210");
        when(roleRepo.findRoleByName(anyString())).thenReturn(new Role());
        given(personalDataRepo.save(any(PersonalData.class))).willReturn(new PersonalData());
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        mockMvc.perform(
                        get("/api/auth/send_code").param("email", email))
                .andExpect(status().isOk());
        verify(userRepo, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleRepo, times(1)).findRoleByName(anyString());
        verify(personalDataRepo, times(1)).save(any(PersonalData.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void refreshTokenWithNoTokenInHeader() throws Exception {
        this.mockMvc.perform(get("/api/auth/token/refresh"))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Refresh token has not been found or wrong")))
                .andExpect(unauthenticated());
    }

    @Test
    void refreshTokenWithTokenInHeader() throws Exception {
        String email = "tymur.foshch@gmail.com";
        given(userRepo.findByEmail(email)).willReturn(Optional.of(new User(email, "1234", new HashSet<>())));
        this.mockMvc.perform(get("/api/auth/token/refresh").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eW11ci5mb3NoY2hAZ21haWwuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MS9hcGkvYXV0aC9zaWduaW4iLCJleHAiOjE2NTg5ODk2MzZ9.OQAVwFOZ73RWfplySVB0inr30A7wLuK3xWK97tFoEAw"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string(containsString("access_token")))
                .andExpect(content()
                        .string(containsString("refresh_token")));
        verify(userRepo, times(1)).findByEmail(email);
    }

}