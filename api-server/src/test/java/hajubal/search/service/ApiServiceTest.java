package hajubal.search.service;

import hajubal.search.api.KeywordApi;
import hajubal.search.api.SearchApi;
import hajubal.search.client.dto.SearchResponse;
import hajubal.search.controller.dto.SearchBlogDto;
import hajubal.search.result.PopularKeywordResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ApiServiceTest {

    @InjectMocks
    private ApiService apiService;

    @Mock
    private SearchApi searchApi;

    @Mock
    private KeywordApi keywordApi;

    @Nested
    @DisplayName("searchBlog 함수는")
    class Describe_of_searchBlog {

        @Nested
        @DisplayName("키워드 검색 요청을 받으면")
        class Describe_of_SearchBlogDto_RequestDto {

            @BeforeEach
            void setup() {
                given(searchApi.search(any(), any(), any(), any()))
                        .willReturn(
                                SearchResponse.of(
                                        SearchResponse.Meta.of(10, 10, true),
                                        List.of(
                                                SearchResponse.Document.of("카카오 제목", "콘텐츠"
                                                        , "url", "블로그명", "썸네일 링크"
                                                        , LocalDate.now())
                                        )
                                )
                        );
            }

            final SearchBlogDto.RequestDto request =
                    SearchBlogDto.RequestDto.builder()
                            .query("korea")
                            .page(1)
                            .size(10)
                            .sort(SearchBlogDto.RequestDto.Sort.recency)
                            .build();

            @DisplayName("검색 결과를 리턴한다.")
            @Test
            void test() {
                apiService.searchBlog(request);
            }
        }
    }

    @Nested
    @DisplayName("popular 함수는")
    class Describe_of_popular {

        @Nested
        @DisplayName("호출하면")
        class Describe_of_call {

            @BeforeEach
            void setup() {
                given(keywordApi.popularKeywords())
                        .willReturn(
                                List.of(PopularKeywordResult.of("한국", 100000L),
                                        PopularKeywordResult.of("미국", 1000L),
                                                PopularKeywordResult.of("러시아", 100L))
                        );
            }

            @DisplayName("결과를 리턴한다.")
            @Test
            void test() {
                assertThat(apiService.popular().getPopularKeywords()).size().isEqualTo(3);
            }
        }
    }
}