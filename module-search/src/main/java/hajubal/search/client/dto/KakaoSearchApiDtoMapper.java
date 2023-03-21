package hajubal.search.client.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoSearchApiDtoMapper {

	public static SearchResponse of(final KakaoSearchApiDto.SearchResponse response) {

		List<SearchResponse.Document> documents = response.getDocuments().stream().map(document -> SearchResponse.Document.of(
				document.getTitle(),
				document.getContents(),
				document.getUrl(),
				document.getBlogname(),
				document.getThumbnail(),
				getRegistrationDate(document.getDatetime())
		)).collect(Collectors.toList());

		SearchResponse.Meta meta = SearchResponse.Meta.of(
				response.getMeta().getTotalCount(),
				response.getMeta().getPageableCount(),
				response.getMeta().getIsEnd()
		);

		return SearchResponse.of(meta, documents);
	}

	private static LocalDate getRegistrationDate(final ZonedDateTime datetime) {
		if (datetime == null) {
			return null;
		}

		return datetime.toLocalDate();
	}

}
