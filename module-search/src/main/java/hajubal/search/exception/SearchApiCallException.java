package hajubal.search.exception;

/**
 * SearchApi 호출 중 발생한 예외
 */
public class SearchApiCallException extends RuntimeException {
    public SearchApiCallException(String message) {
        super(message);
    }
}
