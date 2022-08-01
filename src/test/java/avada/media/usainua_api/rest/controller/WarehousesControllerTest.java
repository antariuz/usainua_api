package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.repository.WarehouseRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WarehousesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseRepo warehouseRepo;

    @Test
    void responseOfGetAllWarehousesShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/getAllWarehouses"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllWarehousesShouldBeOk() throws Exception {
        given(warehouseRepo.findAll()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/getAllWarehouses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("[]"));
        verify(warehouseRepo, times(1)).findAll();
    }

}