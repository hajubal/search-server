package hajubal.search.client;

import hajubal.search.client.dto.KakaoSearchApiDto;
import hajubal.search.client.dto.KakaoSearchApiDtoMapper;
import hajubal.search.client.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

/**
 * kakao 검색 클라이언트
 */
@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class KakaoSearchClient implements SearchClient {

    private final RestTemplate restTemplate;

    @Value("${app.api.kakao.url}")
    private String url;
    @Value("${app.api.kakao.authorization}")
    private String authorization;
    @Value("${app.api.kakao.rest-api-key}")
    private String apiKey;

    @Override
    public SearchResponse search(String query, String sort, Integer page, Integer size) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/v2/search/blog")
                .queryParam("query", query);

        if(StringUtils.hasText(sort)) builder.queryParam("sort", sort);
        if(StringUtils.hasText(sort)) builder.queryParam("page", page);
        if(StringUtils.hasText(sort)) builder.queryParam("size", size);

        URI uri = builder.build().toUri();

        final ResponseEntity<KakaoSearchApiDto.SearchResponse> responseEntity = restTemplate
                .exchange(uri, HttpMethod.GET, getHttpEntity(), KakaoSearchApiDto.SearchResponse.class);

        log.info("uri={}, statusCode={}, responseBody={}", uri, responseEntity.getStatusCode(), responseEntity.getBody());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException("API 응답에 실패.");
        }

        return KakaoSearchApiDtoMapper.of(responseEntity.getBody());
    }

    private HttpEntity getHttpEntity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorization());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(headers);
    }

    private String getAuthorization() {
        return authorization + " " + apiKey;
    }

}
