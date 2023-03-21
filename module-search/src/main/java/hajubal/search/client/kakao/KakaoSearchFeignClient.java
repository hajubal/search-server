package hajubal.search.client.kakao;

import hajubal.search.client.kakao.dto.KakaoSearchApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * kakao 검색 클라이언트
 */
@FeignClient(name = "KakaoSearchClient", url = "${app.api.kakao.url}", configuration = KakaoSearchConfig.class)
public interface KakaoSearchFeignClient {

    @GetMapping(value = "/v2/search/blog", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoSearchApiDto.SearchResponse search(@RequestParam("query") String query,
                                            @RequestParam("sort") String sort,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size);

}
