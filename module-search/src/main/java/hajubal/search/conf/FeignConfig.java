package hajubal.search.conf;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"hajubal.search"})
public class FeignConfig {
}
