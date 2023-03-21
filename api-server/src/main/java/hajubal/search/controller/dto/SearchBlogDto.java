package hajubal.search.controller.dto;

import hajubal.search.client.SearchResponse;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchBlogDto {

    /**
     * 블로그 검색 요청 정보
     */
    @Getter
    @Setter
    @ToString
    @Builder
    public static class RequestDto {

        /** 검색을 원하는 질의어 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음 */
        @NotNull
        @NotEmpty
        private String query;

        /** 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy */
        private Sort sort;

        /** 결과 페이지 번호, 1~50 사이의 값, 기본 값 1 */
        @Min(1) @Max(50)
        private Integer page;

        /** 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10 */
        @Min(1) @Max(50)
        private Integer size;

        /**
         * 결과 문서 정렬 방식
         */
        public enum Sort {
            /** 정확도순 */
            accuracy,
            /** 최신순 */
            recency
        }

        public Sort getSort() {
            if(this.sort == null) return Sort.accuracy;

            return this.sort;
        }
    }

    @ToString
    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class ResponseDto {

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

            /** 정렬 기준 */
            private String sorted;
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

            /** 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz] */
            private LocalDate datetime;
        }

        public static ResponseDto from(SearchResponse response, RequestDto.Sort sort) {
            return ResponseDto.of(
                    Meta.of(response.getMeta().getTotalCount(),
                            response.getMeta().getPageableCount(),
                            response.getMeta().getIsEnd(),
                            sort.name()),
                    response.getDocuments().stream().map(doc -> ResponseDto.Document.of(
                            doc.getTitle(),
                            doc.getContents(),
                            doc.getUrl(),
                            doc.getBlogname(),
                            doc.getDatetime()
                    )).collect(Collectors.toList())
            );
        }
    }
}
