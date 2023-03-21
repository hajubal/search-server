package hajubal.search.api;

import hajubal.search.client.SearchClient;
import hajubal.search.client.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * 블로그 검색 api
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SearchApi {

    private final List<SearchClient> searchClient;

    /**
     * 검색 api
     *
     * @param query 검색 조건
     * @param sort 정렬 기준
     * @param page 시작 페이지
     * @param size 페이지 갯 수
     * @return 검색 결과
     */
    public SearchResponse search(String query, String sort, Integer page, Integer size) {
        for (SearchClient client :  searchClient) {
            try {
                return client.search(query, sort, page, size);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        throw new RestClientException("API 응답에 실패.");
    }

}
