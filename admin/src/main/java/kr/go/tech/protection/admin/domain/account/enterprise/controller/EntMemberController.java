package kr.go.tech.protection.admin.domain.account.enterprise.controller;

import javax.validation.Valid;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.service.EntMemberService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		EntMemberPO.UpdateResponsePO response = entMemberService.updateEntMember(requestPO);
		return ApiResult.success(response);
	}

	 // 사업자 등록번호 중복 체크 API
	 @GetMapping("/check-business-number")
	 public ApiResult<Boolean> checkBusinessNumber(@RequestParam String businessNumber) {
		 boolean isDuplicate = entMemberService.checkBusinessNumber(businessNumber);
		 return ApiResult.success(isDuplicate);
	 }

	// 기업 회원 상세 조회 API
	@GetMapping("/{no}")
	public ApiResult<EntMemberPO.DetailResponsePO> selectEntMemberByNo(@PathVariable int no) {
		EntMemberPO.DetailResponsePO data = entMemberService.selectEntMemberByNo(no);
		return ApiResult.success(data);
	}

	// 기업 회원 - 기업소속 직원 목록 조회 API
	@GetMapping("/employees/{entNo}")
	public ApiResult<EntMemberPO.EmployeeListResponsePO> selectEmployeeListByEntNo(@PathVariable int entNo) {
		EntMemberPO.EmployeeListResponsePO data = entMemberService.selectEmployeeListByEntNo(entNo);
		return ApiResult.success(data);
	}

	// 기업 회원 삭제 API
	@DeleteMapping("/{no}")
	public ApiResult<String> deleteEntMember(@PathVariable int no) {
		entMemberService.deleteEntMember(no);
		return ApiResult.success("");
	}

}


