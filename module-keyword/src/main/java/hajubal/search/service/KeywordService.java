package hajubal.search.service;

import hajubal.search.entity.Keyword;
import hajubal.search.repository.KeywordRepository;
import hajubal.search.result.PopularKeywordResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 키워드 관련 로직 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;

    /**
     * 상위 인기 키워드 조회
     * 검색된 데이터는 caching 됨.
     *
     * @return 상위 인기 키워드 조회 결과
     */
    @Cacheable(value = "keyword")
    @Transactional(readOnly = true)
    public List<PopularKeywordResult> popularKeywords() {
        log.info("popularKeywords call. none cached.");

        List<Keyword> keywords = keywordRepository.findTop10ByOrderByCountDesc();

        return keywords.stream().map(keyword -> PopularKeywordResult.of(keyword.getKeyword(), keyword.getCount())).collect(Collectors.toList());
    }

    /**
     * 키워드 검색 횟수 update
     * cached 데이터 초기화.
     * //-- cache에 key 값이 없어 'allEntries = true'로 전체 초기화 --//
     * @param keyword 검색 키워드
     */
    @CacheEvict(value = "keyword", allEntries = true, condition = "#keyword != null")
    public void searchedKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            log.info("keyword is empty.");
            return;
        }

        updateKeywordCount(keyword);
    }

    /**
     * 없으면 추가하고 있는 경우 횟수 증가
     *
     * @param keyword 검색어
     */
    private void updateKeywordCount(String keyword) {
        if(keywordRepository.findById(keyword).isPresent()) {
            log.info("Keyword count update. keyword: {}", keyword);

            keywordRepository.searchedKeyword(keyword);
        } else {
            log.info("Keyword insert. keyword: {}", keyword);

            keywordRepository.save(Keyword.of(keyword));
        }
    }

}
