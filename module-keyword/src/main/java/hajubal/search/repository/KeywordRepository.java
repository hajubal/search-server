package hajubal.search.repository;

import hajubal.search.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, String> {

    List<Keyword> findTop10ByOrderByCountDesc();

    //-- 정합성 일치를 위한 clearAutomatically, context 데이터 유실 방지를 위한 flushAutomatically--//
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Keyword m set m.count = m.count + 1 where m.keyword = :keyword")
    void searchedKeyword(@Param("keyword") String keyword);
}
