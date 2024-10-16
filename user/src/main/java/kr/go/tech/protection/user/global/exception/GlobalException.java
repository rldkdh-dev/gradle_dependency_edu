package kr.go.tech.protection.user.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {
	@Getter
	ErrorCode errorCode;
}
