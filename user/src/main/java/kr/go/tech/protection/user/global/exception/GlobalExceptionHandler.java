package kr.go.tech.protection.user.global.exception;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import kr.go.tech.protection.user.global.exception.ValidationErrorResponse.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
		MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> ValidationErrorResponse.FieldError.of(
				fieldError.getField(),
				fieldError.getDefaultMessage(),
				fieldError.getRejectedValue()))
			.collect(Collectors.toList());

		ValidationErrorResponse response = ValidationErrorResponse.of(
			HttpStatus.BAD_REQUEST.value(), "유효성 검사 오류", fieldErrors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ValidationErrorResponse> handleConstraintViolation(
		ConstraintViolationException ex) {
		List<ValidationErrorResponse.FieldError> fieldErrors = ex.getConstraintViolations().stream()
			.map(violation -> ValidationErrorResponse.FieldError.of(
				violation.getPropertyPath().toString(),
				violation.getMessage(),
				violation.getInvalidValue()))
			.collect(Collectors.toList());

		ValidationErrorResponse response = ValidationErrorResponse.of(
			HttpStatus.BAD_REQUEST.value(), "유효성 검사 오류", fieldErrors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception ex) {
		return new ResponseEntity<>("서버 에러가 발생했습니다. 관리자에게 문의해주세요", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
