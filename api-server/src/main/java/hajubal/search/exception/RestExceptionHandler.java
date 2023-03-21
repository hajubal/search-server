package hajubal.search.exception;

import hajubal.search.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static hajubal.search.response.ResponseCode.INVALID_PARAMETER;
import static hajubal.search.response.ResponseCode.SYSTEM_ERROR;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * 요청 파라미터 예외
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class, BindException.class})
	public ErrorResponse handleIllegalArgumentException(final Exception e) {
		log.error("handleIllegalArgumentException: ", e);

		return ErrorResponse.of(INVALID_PARAMETER);
	}

	/**
	 * 시스템 에러
	 */
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(final Exception e) {
		log.error("handleException: ", e);

		return ErrorResponse.of(SYSTEM_ERROR);
	}

}
