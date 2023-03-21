package hajubal.search.client.naver;

import hajubal.search.client.naver.dto.NaverSearchApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * kakao 검색 클라이언트
 */
@FeignClient(name = "NaverSearchClient", url = "${app.api.naver.url}", configuration = NaverSearchConfig.class)
public interface NaverSearchFeignClient {

    @GetMapping(value = "/v1/search/blog.json", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverSearchApiDto.SearchResponse search(@RequestParam("query") String query,
                                            @RequestParam("sort") String sort,
                                            @RequestParam("start") Integer start,
                                            @RequestParam("display") Integer display);

}
