package hajubal.search.service;

import hajubal.search.api.KeywordApi;
import hajubal.search.api.SearchApi;
import hajubal.search.client.SearchResponse;
import hajubal.search.controller.dto.PopularKeywordDto;
import hajubal.search.controller.dto.SearchBlogDto;
import hajubal.search.event.SearchEventHandler;
import hajubal.search.result.PopularKeywordResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @MockBean
    private SearchApi searchApi;

    @MockBean
    private KeywordApi keywordApi;

    @MockBean
    private SearchEventHandler handler;

    @Nested
    @DisplayName("searchBlog 함수는 ")
    @RecordApplicationEvents
    class Describe_of_searchBlog {

        @Nested
        @DisplayName("키워드 검색 요청을 받으면 ")
        class Describe_of_SearchBlogDto_RequestDto {
            private final SearchBlogDto.RequestDto request =
                    SearchBlogDto.RequestDto.builder()
                            .query("korea")
                            .page(1)
                            .size(10)
                            .sort(SearchBlogDto.RequestDto.Sort.recency)
                            .build();

            private final SearchResponse response = SearchResponse.of(
                        SearchResponse.Meta.of(10, 10, true),
                        List.of(
                                SearchResponse.Document.of("카카오 제목", "콘텐츠"
                                        , "url", "블로그명", "썸네일 링크"
                                        , LocalDate.now())
                        )
                );

            private SearchBlogDto.ResponseDto responseDto;

            @BeforeEach
            void setup() {
                given(searchApi.search(any(), any(), any(), any()))
                        .willReturn(response);

                responseDto = apiService.searchBlog(request);
            }

            @DisplayName("검색 결과를 리턴한다.")
            @Test
            void searched_return() {
                assertThat(responseDto)
                        .isEqualTo(SearchBlogDto.ResponseDto.from(response, request.getSort()));
            }

            @DisplayName("검색 이벤트 핸들러를 호출한다.")
            @Test
            void handler_call() {
                verify(handler, times(1)).searchedKeyword(any());
            }
        }
    }

    @Nested
    @DisplayName("popular 함수는 ")
    class Describe_of_popular {

        @Nested
        @DisplayName("호출하면 ")
        class Describe_of_call {

            private List<PopularKeywordDto.ResponseDto.PopularKeyword> popularKeywords;

            @BeforeEach
            void setup() {
                given(keywordApi.popularKeywords())
                        .willReturn(
                                List.of(PopularKeywordResult.of("한국", 100000L)
                                        , PopularKeywordResult.of("미국", 1000L)
                                        , PopularKeywordResult.of("러시아", 100L))
                        );

                popularKeywords = apiService.popular().getPopularKeywords();
            }

            @DisplayName("결과를 리턴한다.")
            @Test
            void searched_return() {
                assertThat(popularKeywords).size().isEqualTo(3);
            }
        }
    }
}
