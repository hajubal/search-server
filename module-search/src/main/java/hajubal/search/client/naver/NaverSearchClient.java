package hajubal.search.client.naver;

import hajubal.search.client.SearchClient;
import hajubal.search.client.SearchResponse;
import hajubal.search.client.naver.dto.NaverSearchApiDto;
import hajubal.search.client.naver.dto.NaverSearchApiDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Order(value = 2)
@Component
public class NaverSearchClient implements SearchClient {

    private final NaverSearchFeignClient naverSearchFeignClient;

    @Override
    public SearchResponse search(String query, String sort, Integer page, Integer size) {
        log.info("Search parameter. query: {}, sort: {}, page: {}, size: {}", query, sort, page, size);

        NaverSearchApiDto.SearchResponse response = naverSearchFeignClient.search(query, convertSortFormat(sort), page, size);

        log.info("Naver Search. Response={}", response);

        return NaverSearchApiDtoMapper.of(Optional.ofNullable(page).orElse(0), response);
    }

    private static String convertSortFormat(String sort) {
        if(StringUtils.hasText(sort)) {
            if("accuracy".equals(sort)) return "sim";
            if("recency".equals(sort)) return "date";
        }

        return "sim";
    }

}
