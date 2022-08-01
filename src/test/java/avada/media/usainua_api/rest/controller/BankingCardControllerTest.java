package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.BankingCard;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.BankingCardRepo;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankingCardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankingCardRepo bankingCardRepo;
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
    void responseOfGetAllBankingCardsShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/getAllBankingCards"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllBankingCardsShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(user));
        when(bankingCardRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/getAllBankingCards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
        verify(bankingCardRepo, times(1)).findByUserId(user.getId());
        verify(userRepo, times(1)).findByEmail(anyString());
    }

    @Test
    void responseOfAddBankingCardShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/addBankingCard"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfAddBankingCardShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail(anyString())).willReturn(Optional.of(user));
        when(bankingCardRepo.findByUserId(user.getId())).thenReturn(new ArrayList<>());
        when(bankingCardRepo.save(any(BankingCard.class))).thenReturn(new BankingCard());
        mockMvc.perform(post("/addBankingCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new BankingCard())))
                .andExpect(status().isCreated());
        verify(bankingCardRepo, times(1)).findByUserId(anyLong());
        verify(bankingCardRepo, times(1)).save(any(BankingCard.class));
        verify(userRepo, times(1)).findByEmail(anyString());
    }

    @Test
    @WithMockUser
    void deleteBankingCard() throws Exception {
        willDoNothing().given(bankingCardRepo).deleteById(1L);
        mockMvc.perform(delete("/deleteBankingCard")
                        .param("id", "1"))
                .andExpect(status().isOk());
        verify(bankingCardRepo, times(1)).deleteById(anyLong());
    }

    @Test
    @WithMockUser
    void setDefaultBankingCard() throws Exception {
        when(bankingCardRepo.findBankingCardByMainEquals(true)).thenReturn(new BankingCard());
        when(bankingCardRepo.findById(2L)).thenReturn(Optional.of(new BankingCard()));
        when(bankingCardRepo.save(any(BankingCard.class))).thenReturn(new BankingCard());
        mockMvc.perform(post("/setDefaultBankingCard")
                        .param("id", "2"))
                .andExpect(status().isOk());
        verify(bankingCardRepo, times(1)).findBankingCardByMainEquals(true);
        verify(bankingCardRepo, times(1)).findById(anyLong());
        verify(bankingCardRepo, times(2)).save(any(BankingCard.class));
    }

}
