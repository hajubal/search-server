package hajubal.search.api;

import hajubal.search.result.PopularKeywordResult;
import hajubal.search.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 검색 키워드 관련 api
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KeywordApi {

    private final KeywordService keywordService;

    /**
     * 인기 검색 키워드 조회.
     *
     * @return 상위 인기 검색 결과
     */
    public List<PopularKeywordResult> popularKeywords() {
        log.info("popularKeywords call.");

        return keywordService.popularKeywords();
    }

    /**
     * 키워드 검색 횟수 update.
     *
     * @param keyword 검색 키워드
     */
    public void searchedKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            log.info("keyword is empty.");
            return;
        }

        log.info("searchedKeyword. keyword: {}", keyword);

        keywordService.searchedKeyword(keyword);
    }

}
