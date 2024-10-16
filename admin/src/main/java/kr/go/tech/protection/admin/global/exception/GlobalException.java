package kr.go.tech.protection.admin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {
	@Getter
	ErrorCode errorCode;
}
