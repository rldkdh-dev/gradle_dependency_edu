package kr.go.tech.protection.admin.domain.auth.controller;

import kr.go.tech.protection.admin.domain.auth.dto.AuthPO;
import kr.go.tech.protection.admin.domain.auth.service.AuthService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@GetMapping("/nameList")
	public ApiResult<List<AuthPO.NameResponsePO>> selectAuthNameList() {
		List<AuthPO.NameResponsePO> list = authService.selectAuthNameList();
		return ApiResult.success(list);
	}
}
