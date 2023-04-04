package hajubal.search.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerNonMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void basicTest() throws Exception {
        mockMvc.perform(get("/v1/search/blog")
                        .param("query", "korea")
                        .param("sort", "accuracy")
                        .param("page", "1")
                        .param("size", "10")
                )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
        ;

        mockMvc.perform(get("/v1/keywords/popular"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
        ;
    }

}