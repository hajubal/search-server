package hajubal.search.client.kakao;

import hajubal.search.client.SearchClient;
import hajubal.search.client.SearchResponse;
import hajubal.search.client.kakao.dto.KakaoSearchApiDto;
import hajubal.search.client.kakao.dto.KakaoSearchApiDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * kakao 검색 클라이언트
 */
@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class KakaoSearchClient implements SearchClient {

    private final KakaoSearchFeignClient kakaoSearchFeignClient;

    @Override
    public SearchResponse search(String query, String sort, Integer page, Integer size) {
        log.info("Search parameter. query: {}, sort: {}, page: {}, size: {}", query, sort, page, size);

        KakaoSearchApiDto.SearchResponse response = kakaoSearchFeignClient.search(query, sort, page, size);

        log.info("Kakao Search. Response={}", response);

        return KakaoSearchApiDtoMapper.of(response);
    }

}
