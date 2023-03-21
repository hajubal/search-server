package hajubal.search.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
	INVALID_PARAMETER("E01", "Invalid Parameter"),
	SYSTEM_ERROR("E99", "Internal Server Error");

	private final String code;
	private final String message;
}
