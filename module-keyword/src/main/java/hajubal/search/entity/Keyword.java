package hajubal.search.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Keyword extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(length = 255)
    private String keyword;
    private Long count;

    private Keyword(String keyword) {
        this.keyword = keyword;
        this.count = 1L;
    }

    public static Keyword of(String keyword) {
        if(keyword.length() > 255) keyword = keyword.substring(0, 244);

        return new Keyword(keyword);
    }

    @Override
    public String getId() {
        return getKeyword();
    }

    @Override
    public boolean isNew() {
        return getRegDate() == null;
    }

}
