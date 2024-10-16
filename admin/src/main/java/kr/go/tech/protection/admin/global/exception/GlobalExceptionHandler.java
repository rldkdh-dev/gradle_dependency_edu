package kr.go.tech.protection.admin.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(GlobalException.class)
	protected ResponseEntity<ErrorResponse> globalException(GlobalException e) {
		log.error("Exception ::: ", e.getMessage(), e);
		final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception ex) {
		return new ResponseEntity<>("서버 에러가 발생했습니다. 관리자에게 문의해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
