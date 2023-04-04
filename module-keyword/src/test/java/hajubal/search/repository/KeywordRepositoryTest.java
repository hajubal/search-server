package hajubal.search.repository;

import hajubal.search.conf.JpaConfig;
import hajubal.search.entity.Keyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Import(JpaConfig.class)
@DataJpaTest
class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    void searchedKeywordTest() {

        keywordRepository.save(Keyword.of("test"));

        keywordRepository.searchedKeyword("test");

        Optional<Keyword> test = keywordRepository.findById("test");

        assertThat(test).isPresent();
        assertThat(test.get().getCount()).isEqualTo(2L);
    }
}
