package hajubal.search.conf;

import hajubal.search.controller.dto.SearchBlogDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SortConverter());
    }

    /**
     * SearchBlogDto.RequestDto.Sort type 이 아닌 값이 올 경우 default 값으로 변경
     */
    public static class SortConverter implements Converter<String, SearchBlogDto.RequestDto.Sort> {

        @Override
        public SearchBlogDto.RequestDto.Sort convert(String sort) {
            return Arrays.stream(SearchBlogDto.RequestDto.Sort.values())
                    .filter(sortValue -> sortValue.name().equals(sort)).findAny()
                    .orElse(SearchBlogDto.RequestDto.Sort.accuracy);
        }
    }
}
