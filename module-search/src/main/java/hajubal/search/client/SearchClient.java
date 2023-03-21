package hajubal.search.client;


/**
 * 블로그 검색 인터페이스
 */
public interface SearchClient {

    /**
     * 블로그 검색
     *
     * @param query 검색어
     * @param sort 정렬
     * @param page 현재 페이지
     * @param size 페이지 갯 수
     * @return 검색 결과
     */
    SearchResponse search(String query, String sort, Integer page, Integer size);

}
