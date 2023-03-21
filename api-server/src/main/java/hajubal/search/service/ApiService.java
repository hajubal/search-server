package hajubal.search.service;

import hajubal.search.api.KeywordApi;
import hajubal.search.api.SearchApi;
import hajubal.search.client.SearchResponse;
import hajubal.search.controller.dto.PopularKeywordDto;
import hajubal.search.controller.dto.SearchBlogDto;
import hajubal.search.result.PopularKeywordResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * api 서버스 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiService {

    /** 검색 모듈 api */
    private final SearchApi searchApi;

    /** 인기 검색어 모듈 api */
    private final KeywordApi keywordApi;

    /**
     * 블로그 검색
     *
     * @param requestDto 블로그 검색 조건
     * @return 검색 결과
     */
    public SearchBlogDto.ResponseDto searchBlog(SearchBlogDto.RequestDto requestDto) {
        SearchResponse searchResponse = searchApi.search(requestDto.getQuery(), requestDto.getSort().name()
                , requestDto.getPage(), requestDto.getSize());

        log.info("searchResponse: {}", searchResponse);

        keywordApi.searchedKeyword(requestDto.getQuery());

        return SearchBlogDto.ResponseDto.from(searchResponse, requestDto.getSort());
    }

    /**
     * 상위 인기 키워드
     *
     * @return 상위 인기 키워드 검색 결과
     */
    public PopularKeywordDto.ResponseDto popular() {
        List<PopularKeywordResult> results = keywordApi.popularKeywords();

        return PopularKeywordDto.ResponseDto.of(results.stream().map(popularKeywordResult ->
                PopularKeywordDto.ResponseDto.PopularKeyword.of(popularKeywordResult.getKeyword()
                        , popularKeywordResult.getCount())).collect(Collectors.toList()));
    }

}
