package hajubal.search.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PopularKeywordDto {

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class ResponseDto {

        List<PopularKeyword> popularKeywords;

        @Getter
        @AllArgsConstructor(staticName = "of")
        public static class PopularKeyword {
            private String keyword;
            private Long searchCount;
        }
    }

}
