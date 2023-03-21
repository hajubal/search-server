package hajubal.search.client.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverSearchApiDtoMapper {

	public static SearchResponse of(final Integer start, final NaverSearchApiDto.SearchResponse response) {

		List<SearchResponse.Document> documents = response.getItems().stream().map(document -> SearchResponse.Document.of(
				document.getTitle(),
				document.getDescription(),
				document.getLink(),
				document.getBloggername(),
				null,
				document.getPostdate())
		).collect(Collectors.toList());

		SearchResponse.Meta meta = SearchResponse.Meta.of(
				response.getTotal(),
				response.getDisplay(),
				response.isEnd(start)
		);

		return SearchResponse.of(meta, documents);
	}

}
