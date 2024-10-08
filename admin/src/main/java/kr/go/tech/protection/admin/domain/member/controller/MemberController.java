package kr.go.tech.protection.admin.domain.member.controller;

import kr.go.tech.protection.admin.domain.member.dto.MemberPO;
import kr.go.tech.protection.admin.domain.member.service.MemberService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("")
    public ApiResult<MemberPO.ListResponsePO> selectAdminMemberList(MemberPO.SearchPO searchPO) {
        MemberPO.ListResponsePO data = memberService.selectAdminMemberList(searchPO);

        return ApiResult.success(data);
    }

    @GetMapping("/{no}")
    public ApiResult<MemberPO.DetailResponsePO> selectAdminMemberByNo(@PathVariable int no) {
        MemberPO.DetailResponsePO data = memberService.selectAdminMemberByNo(no);
        return ApiResult.success(data);
    }

    @GetMapping("/checkId")
    public ApiResult<MemberPO.SearchIdResponsePO> selectAdminMemberById(MemberPO.SearchIdRequestPO searchIdRequestPO) throws Exception {
        MemberPO.SearchIdResponsePO data = memberService.selectAdminMemberById(searchIdRequestPO);
        return ApiResult.success(data);
    }

    @DeleteMapping("/{no}")
    public ApiResult<String> deleteAdminMember(@PathVariable int no) {
        memberService.deleteAdminMember(no);
        return ApiResult.success("");
    }

    @PostMapping(value = "")
    public ApiResult<MemberPO.RegResponsePO> insertAdminMember(@Valid @RequestBody MemberPO.RegRequestPO requestPO) {
        MemberPO.RegResponsePO response = memberService.insertAdminMember(requestPO);
        return ApiResult.success(response);
    }

    @PutMapping(value = "/password-change")
    public ApiResult<MemberPO.PasswordResponsePO> passwordUpdateMember(@Valid @RequestBody MemberPO.PasswordRequestPO requestPO) {
        MemberPO.PasswordResponsePO response = memberService.updatePassword(requestPO);
        return ApiResult.success(response);
    }

    @PutMapping(value = "/password-reset")
    public ApiResult<MemberPO.ResetPasswordResponsePO> passwordResetMember(@Valid @RequestBody MemberPO.ResetPasswordRequestPO requestPO) {
        MemberPO.ResetPasswordResponsePO response = memberService.resetPassword(requestPO);
        return ApiResult.success(response);
    }

    @PutMapping("")
    public ApiResult<MemberPO.UpdateResponsePO> updateAdminMember(@Valid @RequestBody MemberPO.UpdateRequestPO requestPO) {
        MemberPO.UpdateResponsePO response = memberService.updateAdminMember(requestPO);
        return ApiResult.success(response);
    }
}