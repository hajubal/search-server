package hajubal.search.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class PopularKeywordResult {

	/** 검색된 키워드 */
	private final String keyword;

	/** 키워드 검색 횟수 */
	private final Long count;
}