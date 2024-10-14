package kr.go.tech.protection.admin.domain.account.general.controller;

import javax.validation.Valid;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.service.GenMemberService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/generals")
public class GenMemberController {

	private final GenMemberService genMemberService;

	// 일반 회원 목록 조회 API
	@GetMapping("")
	public ApiResult<GenMemberPO.ListResponsePO> selectGenMemberList(
		GenMemberPO.SearchPO searchPO) {
		GenMemberPO.ListResponsePO data = genMemberService.selectGenMemberList(searchPO);

		return ApiResult.success(data);
	}

	// 일반 회원 상세 조회 및 소속 기업 정보 조회
	@GetMapping("/{no}")
	public ApiResult<GenMemberPO.DetailResponsePO> selectGenMemberByNo(@PathVariable int no) {
		GenMemberPO.DetailResponsePO data = genMemberService.selectGenMemberByNo(no);
		return ApiResult.success(data);
	}

	// 일반 회원 삭제 API
	@DeleteMapping("/{no}")
	public ApiResult<String> deleteGenMember(@PathVariable int no) {
		genMemberService.deleteGenMember(no);
		return ApiResult.success("");
	}
	// 일반 회원 수정 API
	@PutMapping("")
	public ApiResult<GenMemberPO.UpdateResponsePO> updateGenMember(@Valid @RequestBody GenMemberPO.UpdateRequestPO requestPO) {
		GenMemberPO.UpdateResponsePO response = genMemberService.updateGenMember(requestPO);
		return ApiResult.success(response);
	}


}