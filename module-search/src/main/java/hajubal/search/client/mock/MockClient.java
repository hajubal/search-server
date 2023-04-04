package hajubal.search.client.mock;

import hajubal.search.client.SearchClient;
import hajubal.search.client.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(value = 0)
@Profile("test")
@Component
public class MockClient implements SearchClient {

    @Override
    public SearchResponse search(String query, String sort, Integer page, Integer size) {
        log.info("Search parameter. query: {}, sort: {}, page: {}, size: {}", query, sort, page, size);

        return SearchResponse.of(
                SearchResponse.Meta.of(10, 10, true),
                List.of(
                        SearchResponse.Document.of("제목", "콘텐츠"
                                , "url", "블로그명", "썸네일 링크"
                                , LocalDate.now())
                )
        );
    }

}

