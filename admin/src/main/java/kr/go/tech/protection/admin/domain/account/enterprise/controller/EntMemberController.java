package kr.go.tech.protection.admin.domain.account.enterprise.controller;

import javax.validation.Valid;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.service.EntMemberService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/enterprises")
public class EntMemberController {

	private final EntMemberService entMemberService;

	// 기업 회원 목록 조회 API
	@GetMapping("")
	public ApiResult<EntMemberPO.ListResponsePO> selectEntMemberList(EntMemberPO.SearchPO searchPO) {
		EntMemberPO.ListResponsePO data = entMemberService.selectEntMemberList(searchPO);
		return ApiResult.success(data);
	}

	// 기업 회원 수정 API
	@PutMapping("")
	public ApiResult<EntMemberPO.UpdateResponsePO> updateEntMember(@Valid @RequestBody EntMemberPO.UpdateRequestPO requestPO) {
		EntMemberPO.UpdateResponsePO response = entMemberService.updateGenMember(requestPO);
		return ApiResult.success(response);
	}

	 // 기업 회원 - 사업자 등록번호 중복 체크
	 @GetMapping("/check-business-number")
	 public ApiResult<Boolean> checkBusinessNumber(@RequestParam String businessNumber) {
		 boolean isDuplicate = entMemberService.checkBusinessNumber(businessNumber);
		 return ApiResult.success(isDuplicate);
	 }

}

/*
	// 일반 회원 상세 조회 및 소속 기업 정보 조회
	@GetMapping("/{no}")
	public ApiResult<EntMemberPO.DetailResponsePO> selectGenMemberByNo(@PathVariable int no) {
		EntMemberPO.DetailResponsePO data = genMemberService.selectGenMemberByNo(no);
		return ApiResult.success(data);
	}

	// 일반 회원 삭제 API
	@DeleteMapping("/{no}")
	public ApiResult<String> deleteGenMember(@PathVariable int no) {
		genMemberService.deleteGenMember(no);
		return ApiResult.success("");
	}



	// 일반 회원 - 비밀번호 초기화
	@PutMapping(value = "/password-reset")
	 public ApiResult<EntMemberPO.ResetPasswordResponsePO> passwordResetGenMember(@Valid @RequestBody EntMemberPO.ResetPasswordRequestPO requestPO) {
		 EntMemberPO.ResetPasswordResponsePO response = genMemberService.resetPassword(requestPO);
		 return ApiResult.success(response);
	 }
*/


