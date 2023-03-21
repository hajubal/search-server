package hajubal.search.client.naver;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * naver 검색 헤더 설정
 */
public class NaverSearchConfig {
    @Bean
    public RequestInterceptor naverApiRequestHeader(@Value("${app.api.naver.client-id}") String clientId
            , @Value("${app.api.naver.client-secret}") String clientSecret) {
        return template -> {
            template.header("X-Naver-Client-Id", clientId);
            template.header("X-Naver-Client-Secret", clientSecret);
        };
    }
}
