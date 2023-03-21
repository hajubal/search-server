package hajubal.search.client.kakao;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

/**
 * kakao 검색 헤더 설정
 */
public class KakaoSearchConfig {
    @Bean
    public RequestInterceptor kakaoApiRequestHeader(@Value("${app.api.kakao.rest-api-key}") String token) {
        return template -> template.header(HttpHeaders.AUTHORIZATION, "KakaoAK " + token);
    }
}
