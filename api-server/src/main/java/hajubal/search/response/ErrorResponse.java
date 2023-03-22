package hajubal.search.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;

	public static ErrorResponse of(final ResponseCode responseCode) {
		return new ErrorResponse(responseCode.getCode(), responseCode.getMessage());
	}

	public static ErrorResponse of(final ResponseCode responseCode, final String message) {
		return new ErrorResponse(responseCode.getCode(), message);
	}

}
