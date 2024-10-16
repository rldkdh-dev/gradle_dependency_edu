package kr.go.tech.protection.user.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
	private int status;
	private String errorMessage;

	@Builder
	protected ErrorResponse(final ErrorCode code) {
		this.status = code.getHttpStatus().value();
		this.errorMessage = code.getMessage();
	}
	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}
}
