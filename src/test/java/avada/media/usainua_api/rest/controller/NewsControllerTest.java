package avada.media.usainua_api.rest.controller;

import avada.media.usainua_api.model.News;
import avada.media.usainua_api.repository.NewsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsRepo newsRepo;

    @Test
    void responseOfGetAllNewsByPageShouldBeForbidden() throws Exception {
        mockMvc.perform(get("/api/getAllNewsByPage"))
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void responseOfGetAllNewsByPageShouldBeOk() throws Exception {
        Page<News> newsPage = mock(Page.class);
        when(newsRepo.findAll(any(Pageable.class))).thenReturn(newsPage);
        mockMvc.perform(get("/api/getAllNewsByPage")
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortDirection", "DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content()
                        .string("{\"content\":[],\"pageNo\":0,\"pageSize\":0,\"totalElements\":0,\"totalPages\":0,\"last\":false}"));
        verify(newsRepo, times(1)).findAll(any(Pageable.class));
    }

}

