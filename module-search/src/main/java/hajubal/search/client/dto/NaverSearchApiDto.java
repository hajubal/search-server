package hajubal.search.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverSearchApiDto {

	/**
	 * 블로그 검색 결과
	 */
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class SearchResponse {


		/** 검색된 문서 수 */
		private Integer total;

		/** total_count 중 노출 가능 문서 수 */
		private Integer display;

		/** */
		private List<Items> items;

		public Boolean isEnd(final Integer start) {
			return start >= total;
		}

		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		@ToString
		public static class Items {

			/** 블로그 글 제목 */
			private String title;

			/** 블로그 글 요약 */
			private String description;

			/** 블로그 글 URL */
			private String link;

			/** 블로그의 이름 */
			private String bloggername;

			/** 블로그 글 작성시간 */
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
			private LocalDate postdate;

		}
	}

}
