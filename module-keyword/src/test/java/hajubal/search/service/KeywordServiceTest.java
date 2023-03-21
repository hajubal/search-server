package hajubal.search.service;

import hajubal.search.entity.Keyword;
import hajubal.search.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class KeywordServiceTest {

    @Nested
    @DisplayName("popularKeywords 함수는 ")
    class Describe_of_popularKeywords {
        @Nested
        @DisplayName("캐시 데이터가 있으면 ")
        class Describe_of_cache_data_exist {

            @Autowired
            KeywordService keywordService;

            @MockBean
            KeywordRepository keywordRepository;

            @BeforeEach
            void beforeEach() {
                given(keywordRepository.findTop10ByOrderByCountDesc()).willReturn(List.of(
                        Keyword.of("korea")
                ));

                keywordService.popularKeywords();
            }

            @DisplayName("DB 조회를 하지 않는다.")
            @Test
            void returnData() {
                keywordService.popularKeywords();
                keywordService.popularKeywords();
                keywordService.popularKeywords();

                verify(keywordRepository, times(1)).findTop10ByOrderByCountDesc();
            }
        }
    }
}