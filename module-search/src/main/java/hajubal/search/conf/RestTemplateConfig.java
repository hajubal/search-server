package hajubal.search.conf;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class RestTemplateConfig {

	/** connection time out */
	private static final int CONNECTION_TIME_OUT = 5 * 1000;

	/** read time out */
	private static final int READ_TIME_OUT = 5 * 1000;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setMaxTotal(100);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(poolingHttpClientConnectionManager)
				.build();

		return restTemplateBuilder
				.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
				.setConnectTimeout(Duration.ofMillis(CONNECTION_TIME_OUT))
				.setReadTimeout(Duration.ofMillis(READ_TIME_OUT))
				.build();
	}
}
