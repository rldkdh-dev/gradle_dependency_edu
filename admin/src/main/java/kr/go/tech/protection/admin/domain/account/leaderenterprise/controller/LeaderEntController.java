package kr.go.tech.protection.admin.domain.account.leaderenterprise.controller;

import kr.go.tech.protection.admin.domain.account.leaderenterprise.dto.LeaderEntPO;
import kr.go.tech.protection.admin.domain.account.leaderenterprise.service.LeaderEntService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/leader-enterprises")
public class LeaderEntController {

	private final LeaderEntService leaderEntService;

	// 선도기업 목록 조회 API
	@GetMapping("")
	public ApiResult<LeaderEntPO.ListResponsePO> selectLeaderEntList(@ModelAttribute LeaderEntPO.SearchPO searchPO) {
		LeaderEntPO.ListResponsePO data = leaderEntService.selectLeaderEntList(searchPO);
		return ApiResult.success(data);
	}

	// 선도기업 상세 조회 API

	// 선도기업 등록 API

	// 선도기업 수정 API






/*	// 기업 회원 수정 API
	@PutMapping("")
	public ApiResult<EntMemberPO.UpdateResponsePO> updateEntMember(@Valid @RequestBody EntMemberPO.UpdateRequestPO requestPO) {
		EntMemberPO.UpdateResponsePO response = leaderEntService.updateEntMember(requestPO);
		return ApiResult.success(response);
	}

	 // 사업자 등록번호 중복 체크 API
	 @GetMapping("/check-business-number")
	 public ApiResult<Boolean> checkBusinessNumber(@RequestParam String businessNumber) {
		 boolean isDuplicate = leaderEntService.checkBusinessNumber(businessNumber);
		 return ApiResult.success(isDuplicate);
	 }

	// 기업 회원 상세 조회 API
	@GetMapping("/{no}")
	public ApiResult<EntMemberPO.DetailResponsePO> selectEntMemberByNo(@PathVariable int no) {
		EntMemberPO.DetailResponsePO data = leaderEntService.selectEntMemberByNo(no);
		return ApiResult.success(data);
	}

	// 기업 회원 - 기업소속 직원 목록 조회 API
	@GetMapping("/employees/{entNo}")
	public ApiResult<EntMemberPO.EmployeeListResponsePO> selectEmployeeListByEntNo(@PathVariable int entNo) {
		EntMemberPO.EmployeeListResponsePO data = leaderEntService.selectEmployeeListByEntNo(entNo);
		return ApiResult.success(data);
	}

	// 기업 회원 삭제 API
	@DeleteMapping("/{no}")
	public ApiResult<String> deleteEntMember(@PathVariable int no) {
		leaderEntService.deleteEntMember(no);
		return ApiResult.success("");
	}

	// 기업 회원 등록 API
	@PostMapping("")
	public ApiResult<EntMemberPO.InsertResponsePO> insertEntMember(@Valid @RequestBody EntMemberPO.InsertRequestPO requestPO) {
		EntMemberPO.InsertResponsePO response = leaderEntService.insertEntMember(requestPO);
		return ApiResult.success(response);
	}*/

}


