package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.delivery.ShippingAddress;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.ShippingAddressRepo;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShippingAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private ShippingAddressRepo shippingAddressRepo;

    @Test
    void responseOfGetAllShippingAddressesShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/getAllShippingAddresses"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllShippingAddressesShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail("user")).willReturn(Optional.of(user));
        given(shippingAddressRepo.findByUserId(user.getId())).willReturn(new ArrayList<>());
        mockMvc.perform(get("/getAllShippingAddresses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string(new ObjectMapper().writeValueAsString(new ArrayList<>())));
        verify(userRepo, times(1)).findByEmail("user");
        verify(shippingAddressRepo, times(1)).findByUserId(user.getId());
    }

    @Test
    void responseOfAddShippingAddressShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/addShippingAddress"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfAddShippingAddressShouldBeOk() throws Exception {
        User user = getUser();
        when(userRepo.findByEmail("user")).thenReturn(Optional.of(user));
        when(shippingAddressRepo.findByUserId(user.getId())).thenReturn(new ArrayList<>());
        when(shippingAddressRepo.save(any(ShippingAddress.class))).thenReturn(new ShippingAddress());
        mockMvc.perform(post("/addShippingAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShippingAddress())))
                .andExpect(status().isCreated());
        verify(userRepo, times(1)).findByEmail("user");
        verify(shippingAddressRepo, times(1)).findByUserId(user.getId());
        verify(shippingAddressRepo, times(1)).save(any(ShippingAddress.class));
    }

    @Test
    void responseOfUpdateShippingAddressShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/updateShippingAddress"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfUpdateShippingAddressShouldBeOk() throws Exception {
        User user = getUser();
        when(userRepo.findByEmail("user")).thenReturn(Optional.of(user));
        when(shippingAddressRepo.save(any(ShippingAddress.class))).thenReturn(new ShippingAddress());
        mockMvc.perform(post("/addShippingAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShippingAddress())))
                .andExpect(status().isCreated());
        verify(userRepo, times(1)).findByEmail("user");
        verify(shippingAddressRepo, times(1)).save(any(ShippingAddress.class));
    }

    @Test
    void responseOfDeleteShippingAddressShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/deleteShippingAddress"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfDeleteShippingAddressShouldBeOk() throws Exception {
        doNothing().when(shippingAddressRepo).deleteById(1L);
        mockMvc.perform(delete("/deleteShippingAddress").param("id", "1"))
                .andExpect(status().isOk());
        verify(shippingAddressRepo).deleteById(1L);
    }

    @Test
    void responseOfSetDefaultShippingAddressShouldBeForbidden() throws Exception {
        mockMvc.perform(patch("/setDefaultShippingAddress"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfSetDefaultShippingAddressShouldBeOk() throws Exception {
        when(shippingAddressRepo.findShippingAddressByMainEquals(true)).thenReturn(new ShippingAddress());
        when(shippingAddressRepo.findById(1L)).thenReturn(Optional.of(new ShippingAddress()));
        when(shippingAddressRepo.save(any(ShippingAddress.class))).thenReturn(new ShippingAddress());
        mockMvc.perform(patch("/setDefaultShippingAddress").param("id", "1"))
                .andExpect(status().isOk());
        verify(shippingAddressRepo, times(1)).findShippingAddressByMainEquals(true);
        verify(shippingAddressRepo, times(2)).save(any(ShippingAddress.class));
        verify(shippingAddressRepo, times(1)).findById(1L);
    }

    @Test
    void responseOfGetAllCountriesShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/getAllCountries"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllCountriesShouldBeOk() throws Exception {
        mockMvc.perform(get("/getAllCountries").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string(new ObjectMapper().writeValueAsString(ShippingAddress.Country.values())));
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("bob@gmail.com");
        user.setPassword("123");
        user.setRoles(new HashSet<>());
        return user;
    }

}