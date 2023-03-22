package hajubal.search.controller;

import hajubal.search.controller.dto.PopularKeywordDto;
import hajubal.search.controller.dto.SearchBlogDto;
import hajubal.search.service.ApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ApiService apiService;

    @Nested
    @DisplayName("ApiController 클래스의")
    class Describe_of_ApiController {

        @Nested
        @DisplayName("searchBlog 메소드는")
        class Context_with_searchBlog {

            @Nested
            @DisplayName("블로그 검색 요청을 받으면")
            class Context_with_SearchBlogRequest {

                @BeforeEach
                void setup() {
                    given(apiService.searchBlog(any()))
                            .willReturn(getSearchBlogResponse());
                }

                @Test
                @DisplayName("검색 결과를 페이징처리하여 반환한다.")
                void it_returns_paging() throws Exception {

                    mockMvc.perform(get("/v1/search/blog")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .param("query", "korea")
                                    .param("sort", "recency")
                                    .param("page", "1")
                                    .param("size", "10"))
                            .andDo(print()).andExpect(status().isOk())
                            .andExpect(jsonPath("$.meta.totalCount").value(TOTAL_COUNT))
                    ;
                }
            }

            @Nested
            @DisplayName("블로그 검색에 필수 값이 없으면")
            class Context_with_NotValidSearchBlogRequest {
                @Test
                @DisplayName("status code 400을 리턴한다.")
                void not_exist_query() throws Exception {

                    mockMvc.perform(get("/v1/search/blog")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .param("sort", "recency")
                                    .param("page", "1")
                                    .param("size", "10"))
                            .andDo(print()).andExpect(status().isBadRequest());
                }
            }

            @Nested
            @DisplayName("인기 검색 요청을 받으면")
            class Context_with_PopularKeywordRequest {

                @BeforeEach
                void setup() {
                    given(apiService.popular())
                            .willReturn(getPopularKeywordResponse());
                }

                @Test
                @DisplayName("인기 검색어 상위10개를 결과를 반환한다.")
                void it_returns() throws Exception {

                    mockMvc.perform(get("/v1/keywords/popular")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                            .andDo(print()).andExpect(status().isOk())
                            .andExpect(jsonPath("$.popularKeywords.length()").value(2));
                }
            }
        }
    }

    private final Integer TOTAL_COUNT = 1000;

    private SearchBlogDto.ResponseDto getSearchBlogResponse() {
        return SearchBlogDto.ResponseDto.of(
                SearchBlogDto.ResponseDto.Meta.of(TOTAL_COUNT, 100, false, SearchBlogDto.RequestDto.Sort.accuracy.name()),
                List.of(
                        SearchBlogDto.ResponseDto.Document.of(
                                "제목"
                                , "요약"
                                , "url"
                                , "블로그명"
                                , LocalDate.now()
                        ))
        );
    }

    private PopularKeywordDto.ResponseDto getPopularKeywordResponse() {
        return PopularKeywordDto.ResponseDto.of(
                List.of(
                        PopularKeywordDto.ResponseDto.PopularKeyword.of("keyword", 10L) ,
                        PopularKeywordDto.ResponseDto.PopularKeyword.of("keyword2", 100L)
                        ));
    }
}
