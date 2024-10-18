package kr.go.tech.protection.admin.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다\n 관리자에게 문의해주세요."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다."),
	NO_UPDATE(HttpStatus.NOT_FOUND, "업데이트가 취소 되었습니다."),
	NO_BUSINESS_NUMBER(HttpStatus.NOT_FOUND, "유효하지 않은 사업자 등록번호입니다."),
	PASSWORD_RESET_FAILED(HttpStatus.UNAUTHORIZED, "비밀번호가 변경되지 않았습니다."),
	INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원 추가에 실패했습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
