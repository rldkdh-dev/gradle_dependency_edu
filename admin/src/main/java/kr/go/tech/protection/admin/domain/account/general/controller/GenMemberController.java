package kr.go.tech.protection.admin.domain.account.general.controller;

import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.service.GenMemberService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

/*
    // 기업 회원 목록 조회 API
    @GetMapping("/enterprises")
    public ApiResult<GenMemberPO.ListResponsePO> selectGenMemberList(GenMemberPO.SearchPO searchPO) {
        GenMemberPO.ListResponsePO data = genMemberService.selectGenMemberList(searchPO);

        return ApiResult.success(data);
    }
*/

}