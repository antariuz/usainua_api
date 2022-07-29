package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.Product;
import avada.media.usainua_api.repository.ProductRepo;
import avada.media.usainua_api.repository.ShoppingMallRepo;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private ShoppingMallRepo shoppingMallRepo;

    @Test
    void responseOfGetAllProductsByPageShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getProductsByPage"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllProductsByPageShouldBeOk() throws Exception {
        Page<Product> newsPage = mock(Page.class);
        when(productRepo.findAll(any(Pageable.class))).thenReturn(newsPage);
        mockMvc.perform(get("/api/getProductsByPage").contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "id")
                        .param("sortDirection", "DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("{\"content\":[],\"pageNo\":0,\"pageSize\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
        verify(productRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void responseOfGetAllCategoriesShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getAllCategories"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllCategoriesShouldBeOk() throws Exception {
        mockMvc.perform(get("/api/getAllCategories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("[\"TOP\",\"SHOES\",\"CLOTHES\",\"ELECTRONIC\",\"GAMING\"]"));
    }

    @Test
    void responseOfGetAllShoppingMallsShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getAllShoppingMalls"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllShoppingMallsShouldBeOk() throws Exception {
        given(shoppingMallRepo.findAll()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/api/getAllShoppingMalls").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("[]"));
        verify(shoppingMallRepo, times(1)).findAll();
    }

}