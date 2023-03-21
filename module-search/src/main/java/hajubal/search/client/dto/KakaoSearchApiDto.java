package hajubal.search.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoSearchApiDto {

	/**
	 * 블로그 검색 결과
	 */
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class SearchResponse {
		private Meta meta;
		private List<Document> documents;

		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		@ToString
		public static class Meta {

			/** 검색된 문서 수 */
			@JsonProperty("total_count")
			private Integer totalCount;

			/** total_count 중 노출 가능 문서 수 */
			@JsonProperty("pageable_count")
			private Integer pageableCount;

			/** 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 */
			@JsonProperty("is_end")
			private Boolean isEnd;
		}

		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		@ToString
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

			/** 블로그 글 작성시간 */
			private ZonedDateTime datetime;

		}
	}

}
