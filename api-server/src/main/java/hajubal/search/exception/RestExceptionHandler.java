package hajubal.search.exception;

import hajubal.search.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResponse handleIllegalArgumentException(final Exception e) {
		log.error("handleIllegalArgumentException: ", e);

		return ErrorResponse.of(INVALID_PARAMETER);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ErrorResponse handleBindException(final BindException e) {
		log.error("handleBindException: ", e);

		String collectMessage = e.getBindingResult().getFieldErrors()
				.stream()
				.map(error -> error.getField() + " : " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		return ErrorResponse.of(INVALID_PARAMETER, collectMessage);
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
