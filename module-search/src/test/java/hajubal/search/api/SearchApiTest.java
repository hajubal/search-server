package hajubal.search.api;

import feign.RetryableException;
import hajubal.search.client.SearchClient;
import hajubal.search.client.SearchResponse;
import hajubal.search.client.kakao.KakaoSearchClient;
import hajubal.search.client.naver.NaverSearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SearchApiTest {

    @InjectMocks
    private SearchApi client;

    @Spy
    private List<SearchClient> searchClient = new ArrayList<>();

    @Mock
    private KakaoSearchClient kakaoSearchClient;

    @Mock
    private NaverSearchClient naverSearchClient;

    @BeforeEach
    public void before() {
        searchClient.add(kakaoSearchClient);
        searchClient.add(naverSearchClient);
    }

    @Nested
    @DisplayName("search 함수는")
    class Describe_of_search {

        private final SearchResponse response = SearchResponse.of(
                SearchResponse.Meta.of(10, 10, true),
                List.of(
                        SearchResponse.Document.of("카카오 제목", "콘텐츠"
                                , "url", "블로그명", "썸네일 링크"
                                , LocalDate.now())
                )
        );

        @Nested
        @DisplayName("키워드 검색 요청을 받으면")
        class Describe_of_SearchResponse {

            @BeforeEach
            void setup() {
                given(client.search("korea", null, 1, 10))
                        .willReturn(response);
            }

            @DisplayName("KakaoSearchClient를 호출하고")
            @Test
            void test2() {
                client.search("korea", null, 1, 10);

                verify(kakaoSearchClient, times(1)).search(any(), any(), any(), any());
                verify(naverSearchClient, times(0)).search(any(), any(), any(), any());
            }

            @DisplayName("검색 결과를 리턴한다.")
            @Test
            void test() {
//                when(client.search("korea", null, 1, 10))
//                        .thenReturn(response);

                assertThat(client.search("korea", null, 1, 10)).isEqualTo(response);
            }
        }

        @Nested
        @DisplayName("Kakao 서버가 장애가 발생하면")
        class Describe_of_KakaoError {

            @BeforeEach
            void setup() {
                given(kakaoSearchClient.search("korea", null, 1, 10))
                        .willThrow(RetryableException.class);

                given(naverSearchClient.search("korea", null, 1, 10))
                        .willReturn(response);

                client.search("korea", null, 1, 10);
            }

            @DisplayName("NaverSearchClient를 호출한다.")
            @Test
            void test2() {
                verify(kakaoSearchClient, times(1)).search(any(), any(), any(), any());
                verify(naverSearchClient, times(1)).search(any(), any(), any(), any());
            }
        }
    }
}