package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.user.PersonalData;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.PersonalDataRepo;
import avada.media.usainua_api.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonalDataControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonalDataRepo personalDataRepo;
    @MockBean
    private UserRepo userRepo;

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("bob@gmail.com");
        user.setPassword("123");
        user.setRoles(new HashSet<>());
        return user;
    }

    @Test
    void responseOfGetPersonalDataShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/getPersonalData"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetPersonalDataShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(user));
        given(personalDataRepo.findById(anyLong())).willReturn(Optional.of(new PersonalData()));
        mockMvc.perform(get("/getPersonalData").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(new ObjectMapper().writeValueAsString(new PersonalData())));
        verify(userRepo, times(1)).findByEmail(anyString());
        verify(personalDataRepo, times(1)).findById(anyLong());
    }

    @Test
    void responseOfUpdatePersonalDataShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/updatePersonalData"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfUpdatePersonalDataShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(user));
        given(personalDataRepo.findById(anyLong())).willReturn(Optional.of(new PersonalData()));
        when(personalDataRepo.save(any(PersonalData.class))).thenReturn(new PersonalData());
        mockMvc.perform(get("/getPersonalData").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userRepo, times(1)).findByEmail(anyString());
        verify(personalDataRepo, times(1)).findById(anyLong());
    }

}