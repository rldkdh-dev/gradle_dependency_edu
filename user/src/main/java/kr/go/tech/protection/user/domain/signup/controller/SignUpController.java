package kr.go.tech.protection.user.domain.signup.controller;

import javax.validation.Valid;
import kr.go.tech.protection.user.domain.signup.dto.SignUpPO;
import kr.go.tech.protection.user.domain.signup.service.SignUpService;
import kr.go.tech.protection.user.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signup")
public class SignUpController {

	private final SignUpService signUpService;

	// 일반회원 가입 API
	@PostMapping("/generals")
	public ApiResult<SignUpPO.GenSignUpResponsePO> SignUpGenMember(@Valid @RequestBody SignUpPO.GenSignUpRequestPO requestPO) {
		SignUpPO.GenSignUpResponsePO response = signUpService.SignUpGenMember(requestPO);
		return ApiResult.success(response);
	}

	// 일반회원- PASS 인증을 통한 가입여부 조회
	@GetMapping("/generals/check")
	public ApiResult<Boolean> checkSignupStatusByPassAuth(@ModelAttribute SignUpPO.PassAuthRequestPO requestPO) {
		Boolean response = signUpService.checkSignupStatusByPassAuth(requestPO);
		return ApiResult.success(response);
	}

	// 일반회원 - 아이디 중복 체크 API
	 @GetMapping("/check-id")
	 public ApiResult<Boolean> checkGenMemberIdDuplicate(@RequestParam String searchId) {
		 boolean isDuplicate = signUpService.checkGenMemberIdDuplicate(searchId);
		 return ApiResult.success(isDuplicate);
	 }

	// 일반회원 - 사업자등록번호로 기업회원 가입여부 확인 API
	@GetMapping("/check-business-number")
	public ApiResult<Boolean> isBusinessNumberEntMemberRegistered(@RequestParam String businessNumber) {
		boolean isRegistered = signUpService.isBusinessNumberEntMemberRegistered(businessNumber);
		return ApiResult.success(isRegistered);
	}

	// 기업회원 가입 API
	@PostMapping("/enterprises")
	public ApiResult<SignUpPO.EntSignUpResponsePO> SignUpEntMember(@Valid @RequestBody SignUpPO.EntSignUpRequestPO requestPO) {
		SignUpPO.EntSignUpResponsePO response = signUpService.SignUpEntMember(requestPO);
		return ApiResult.success(response);
	}

	// 기업회원 - 공동인증서를 통한 가입여부 조회
	@GetMapping("/enterprises/check")
	public ApiResult<Boolean> checkSignupStatusByJointCertInfo(@ModelAttribute SignUpPO.JointCertInfoRequestPO requestPO) {
		Boolean response = signUpService.checkSignupStatusByJointCertInfo(requestPO);
		return ApiResult.success(response);
	}

}