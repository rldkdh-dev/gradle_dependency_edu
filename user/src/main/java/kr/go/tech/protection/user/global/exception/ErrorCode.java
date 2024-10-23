package kr.go.tech.protection.user.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다\n 관리자에게 문의해주세요."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다."),
	NO_UPDATE(HttpStatus.NOT_FOUND, "업데이트가 취소 되었습니다."),
	NO_BUSINESS_NUMBER(HttpStatus.NOT_FOUND, "유효하지 않은 사업자 등록번호입니다."),
	ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
	SIGN_UP_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패했습니다."),
	ENT_PRCPT_REGISTRATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "기업소속 회원 등록에 실패했습니다."),

	NOT_FOUND_BIZ(HttpStatus.NOT_FOUND, "사업공고가 존재하지 않습니다."),
	NOT_FOUND_TEMP_SAVE(HttpStatus.NOT_FOUND, "사업공고 임시저장 데이터가 존재하지 않습니다."),
	NOT_MATCHED_PIC(HttpStatus.BAD_REQUEST, "담당자 정보가 일치하지 않습니다."),
	BAD_REQUEST_RECRUIT_DATE(HttpStatus.BAD_REQUEST, "모집종료일자는 모집시작일자 이전으로 설정할 수 없습니다."),
	BAD_REQUEST_RECRUIT_DATE_FOR_TODAY(HttpStatus.BAD_REQUEST, "모집종료일자는 오늘 날짜 이전으로 설정할 수 없습니다."),
	BIZ_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "사업 공고 수정에 실패했습니다."),
	TERMS_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "약관 삭제에 실패했습니다."),
	TERMS_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "약관 추가에 실패했습니다."),
	TEMP_SAVE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "임시저장 데이터 삭제에 실패했습니다."),
	TEMP_SAVE_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "임시 저장 데이터 추가에 실패했습니다."),
	BAD_REQUEST_MY_DEPT(HttpStatus.BAD_REQUEST, "자신의 담당부서 선택은 필수입니다."),
	BIZ_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "사업공고 추가에 실패했습니다."),
	BAD_REQUEST_PAGE_NO(HttpStatus.BAD_REQUEST, "올바르지 않는 사업 요청 페이지 번호입니다."),
	INVALID_DEPT_GROUP(HttpStatus.UNAUTHORIZED, "해당 공고에 대한 조회 권한이 없습니다.")
	;

	private final HttpStatus httpStatus;
	private final String message;
}
