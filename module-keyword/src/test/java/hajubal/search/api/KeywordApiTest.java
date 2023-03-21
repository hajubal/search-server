package hajubal.search.api;

import hajubal.search.result.PopularKeywordResult;
import hajubal.search.service.KeywordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class KeywordApiTest {

    @InjectMocks
    private KeywordApi keywordApi;

    @Mock
    private KeywordService keywordService;

    @Nested
    @DisplayName("searchedKeyword 함수는 ")
    class Describe_of_searchedKeyword {

        @Nested
        @DisplayName("데이터를 검색하면 ")
        class Describe_of_search {
            @BeforeEach
            void beforeEach() {
                keywordApi.searchedKeyword("k");
            }

            @DisplayName("searchedKeyword 함수를 호출한다.")
            @Test
            void call() {
                verify(keywordService, times(1)).searchedKeyword(any());
            }
        }
    }

    @Nested
    @DisplayName("popular 함수는 ")
    class Describe_of_popular {

        @Nested
        @DisplayName("검색 데이터가 있으면 ")
        class Describe_of_data_exist {
            @BeforeEach
            void beforeEach() {
                given(keywordService.popularKeywords())
                        .willReturn(
                                List.of(
                                        PopularKeywordResult.of("korea", 1000L),
                                        PopularKeywordResult.of("america", 100L),
                                        PopularKeywordResult.of("russia", 100L)
                                )
                        );
            }

            @DisplayName("데이터를 반환 한다.")
            @Test
            void returnData() {
                assertThat(keywordApi.popularKeywords()).size().isEqualTo(3);
            }
        }

        @Nested
        @DisplayName("검색 데이터가 없으면 ")
        class Describe_of_data_not_exist {
            @BeforeEach
            void beforeEach() {
                given(keywordService.popularKeywords())
                        .willReturn(List.of());
            }

            @DisplayName("빈 데이터를 반환 한다.")
            @Test
            void returnEmptyData() {
                assertThat(keywordApi.popularKeywords()).size().isEqualTo(0);
            }
        }
    }

}
