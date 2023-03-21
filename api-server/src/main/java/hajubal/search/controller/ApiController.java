package hajubal.search.controller;

import hajubal.search.controller.dto.PopularKeywordDto;
import hajubal.search.controller.dto.SearchBlogDto;
import hajubal.search.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * api 제공 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class ApiController {

    private final ApiService apiService;

    /**
     * 블로그 검색
     *
     * @param requestDto 블로그 검색 요청 정보
     * @return 검색 결과
     */
    @GetMapping("/search/blog")
    public SearchBlogDto.ResponseDto searchBlog(@Valid SearchBlogDto.RequestDto requestDto) {
        return apiService.searchBlog(requestDto);
    }

    /**
     * 상위 인기 키워드
     *
     * @return 상위 인기 키워드 검색 결과
     */
    @GetMapping("/keywords/popular")
    public PopularKeywordDto.ResponseDto popular() {
        return apiService.popular();
    }

}
