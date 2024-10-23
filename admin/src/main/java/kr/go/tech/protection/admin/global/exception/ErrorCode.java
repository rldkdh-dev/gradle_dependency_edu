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
	INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원 추가에 실패했습니다."),


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
	INVALID_DEPT_GROUP(HttpStatus.UNAUTHORIZED, "해당 공고에 대한 조회 권한이 없습니다."),
	BAD_REQUEST_PROCESS_BLOCK(HttpStatus.BAD_REQUEST, "프로세스 블록의 첫번째 블록은 신청접수, 마지막 블록은 종료 블록으로 해야 합니다."),
	BAD_REQUEST_UPDATE_PAGE_NO(HttpStatus.BAD_REQUEST, "모집중인 공고는 사업 기본정보만 수정할 수 있습니다."),
	NOT_FOUND_ADMIN(HttpStatus.NOT_FOUND, "관리자 회원이 존재하지 않습니다."),
	ADMIN_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "관리자 회원 삭제에 실패했습니다."),
	DUPLICATION_ADMIN(HttpStatus.BAD_REQUEST, "이미 존재하는 관리자 회원입니다."),
	ADMIN_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "관리자 회원 추가에 실패했습니다."),
	NOT_MATCHED_ADMIN_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
	NOT_MATCHED_ADMIN_CHECK_PASSWORD(HttpStatus.BAD_REQUEST,"입력하신 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
	ADMIN_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "관리자 회원 수정에 실패했습니다.");



	private final HttpStatus httpStatus;
	private final String message;
}
