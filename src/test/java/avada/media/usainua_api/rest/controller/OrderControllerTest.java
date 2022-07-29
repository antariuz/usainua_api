package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.dto.OrderDTO;
import avada.media.usainua_api.model.order.Order;
import avada.media.usainua_api.model.order.SubOrder;
import avada.media.usainua_api.model.user.User;
import avada.media.usainua_api.repository.OrderRepo;
import avada.media.usainua_api.repository.SubOrderRepo;
import avada.media.usainua_api.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderRepo orderRepo;
    @MockBean
    private SubOrderRepo subOrderRepo;
    @MockBean
    private UserRepo userRepo;

    @Test
    void responseOfGetOrdersByPageShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getOrdersByPage"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @Disabled
    @WithMockUser
    void responseOfGetOrdersByPageShouldBeOk() throws Exception {
        User user = getUser();
        given(userRepo.findByEmail("user")).willReturn(Optional.of(user));
        Page<Order> orders = mock(Page.class);
        when(orderRepo.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(orders);
        mockMvc.perform(get("/api/getOrdersByPage").contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "id")
                        .param("sortDirection", "DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("{\"content\":[],\"pageNo\":0,\"pageSize\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
        verify(orderRepo, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
        verify(userRepo, times(1)).findByEmail("user");
    }

    @Test
    void responseOfAddOrderShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/addOrder"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfAddOrderShouldBeOk() throws Exception {
        User user = getUser();
        when(userRepo.findByEmail("user")).thenReturn(Optional.of(user));
        when(subOrderRepo.saveAll(anyCollection())).thenReturn(new ArrayList<>());
        when(orderRepo.save(any(Order.class))).thenReturn(new Order());
        mockMvc.perform(post("/api/addOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new OrderDTO())))
                .andExpect(status().isCreated());
        verify(userRepo, times(1)).findByEmail("user");
        verify(subOrderRepo, times(1)).saveAll(anyCollection());
        verify(orderRepo, times(1)).save(any(Order.class));
    }

    @Test
    void responseOfGetOrderByIdShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getOrderById"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetOrderByIdShouldBeOk() throws Exception {
        User user = getUser();
        when(userRepo.findByEmail("user")).thenReturn(Optional.of(user));
        when(orderRepo.findOrderByIdAndUserId(1L, user.getId())).thenReturn(new Order());
        mockMvc.perform(get("/api/getOrderById")
                        .param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string(new ObjectMapper().writeValueAsString(new Order())));
        verify(userRepo, times(1)).findByEmail("user");
        verify(orderRepo, times(1)).findOrderByIdAndUserId(1L, 1L);

    }

    @Test
    void responseOfDeleteOrderShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/deleteOrder"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfDeleteOrderShouldBeOk() throws Exception {
        doNothing().when(orderRepo).deleteById(1L);
        mockMvc.perform(delete("/api/deleteOrder").param("id", "1"))
                .andExpect(status().isOk());
        verify(orderRepo, times(1)).deleteOrderById(1L);
    }

    @Test
    void responseOfGetAllAdditionalServicesShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getAllAdditionalServices"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllAdditionalServicesShouldBeOk() throws Exception {
        mockMvc.perform(get("/api/getAllAdditionalServices").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string(new ObjectMapper().writeValueAsString(SubOrder.AdditionalServices.values())));
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