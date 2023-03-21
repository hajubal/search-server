package hajubal.search.client;

import hajubal.search.client.dto.NaverSearchApiDto;
import hajubal.search.client.dto.NaverSearchApiDtoMapper;
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

@Slf4j
@RequiredArgsConstructor
@Order(value = 2)
@Component
public class NaverSearchClient implements SearchClient {

    private final RestTemplate restTemplate;

    @Value("${app.api.naver.url}")
    private String url;
    @Value("${app.api.naver.client-id}")
    private String clientId;
    @Value("${app.api.naver.client-secret}")
    private String clientSecret;

    @Override
    public SearchResponse search(String query, String sort, Integer page, Integer size) {

        log.info("query: {}, sort: {}, page: {}, size: {}", query, sort, page, size);

        URI uri = UriComponentsBuilder.fromHttpUrl(url + "/v1/search/blog.json")
                .queryParam("query", query)
                .queryParam("sort", convertSortFormat(sort))
                .queryParam("start", page)
                .queryParam("display", size)
                .build()
                .toUri();

        final ResponseEntity<NaverSearchApiDto.SearchResponse> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, getHttpEntity(), NaverSearchApiDto.SearchResponse.class);

        log.info("uri: {}, statusCode: {}, responseBody: {}", uri, responseEntity.getStatusCode(), responseEntity.getBody());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException("API 응답에 실패.");
        }

        return NaverSearchApiDtoMapper.of(page, responseEntity.getBody());
    }

    private static String convertSortFormat(String sort) {
        if(StringUtils.hasText(sort)) {
            if("accuracy".equals(sort)) return "sim";
            if("recency".equals(sort)) return "date";
        }

        return "sim";
    }

    private HttpEntity getHttpEntity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(headers);
    }

}
