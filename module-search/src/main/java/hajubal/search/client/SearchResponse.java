package hajubal.search.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@AllArgsConstructor(staticName = "of")
@Getter
public class SearchResponse {

    /** meta 정보 */
    private Meta meta;

    /** 문서 정보 */
    private List<Document> documents;

    @ToString
    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Meta {

        /** 검색된 문서 수*/
        private Integer totalCount;

        /** total_count 중 노출 가능 문서 수 */
        private Integer pageableCount;

        /** 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 */
        private Boolean isEnd;
    }

    @ToString
    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Document {

        /** 블로그 글 제목 */
        private String title;

        /** 블로그 글 요약 */
        private String contents;

        /** 블로그 글 URL */
        private String url;

        /** 블로그의 이름 */
        private String blogname;

        /** 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음 */
        private String thumbnail;

        /** 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz] */
        private LocalDate datetime;
    }

}
