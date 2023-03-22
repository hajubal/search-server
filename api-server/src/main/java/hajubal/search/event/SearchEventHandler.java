package hajubal.search.event;

import hajubal.search.api.KeywordApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 검색 이벤트 처리
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SearchEventHandler {

    /** 인기 검색어 모듈 api */
    private final KeywordApi keywordApi;

    /**
     * 키워드 검색 후 처리
     *
     * @param eventDto 전달 객체
     */
    @Async
    @EventListener
    public void searchedKeyword(SearchEventDto eventDto) {
        log.info("SearchEvent fired. query: {}", eventDto.getQuery());

        keywordApi.searchedKeyword(eventDto.getQuery());

        log.info("SearchEvent process complete.");
    }
}
