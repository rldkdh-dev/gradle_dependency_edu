package kr.go.tech.protection.admin.domain.member.controller;

import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.domain.member.service.AdminService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    public ApiResult<AdminPO.ListResponsePO> selectAdminMemberList(AdminPO.SearchPO searchPO){
        AdminPO.ListResponsePO data = adminService.selectAdminMemberList(searchPO);

        return ApiResult.success(data);
    }

    @GetMapping("/{no}")
    public ApiResult<AdminPO.DetailResponsePO> selectAdminMemberByNo(@PathVariable int no) {
        AdminPO.DetailResponsePO data = adminService.selectAdminMemberByNo(no);
        return ApiResult.success(data);
    }

    @GetMapping("/checkId")
    public ApiResult<AdminPO.SearchIdResponsePO> selectAdminMemberById(@Valid @RequestBody AdminPO.SearchIdRequestPO searchIdRequestPO) {
        AdminPO.SearchIdResponsePO data = adminService.selectAdminMemberById(searchIdRequestPO);
        return ApiResult.success(data);
    }

    @DeleteMapping("/{no}")
    public ApiResult<String> deleteAdminMember(@PathVariable int no) {
        adminService.deleteAdminMember(no);
        return ApiResult.success("");
    }

    @PostMapping(value = "")
    public ApiResult<AdminPO.RegResponsePO> insertAdminMember(@Valid @RequestBody AdminPO.RegRequestPO requestPO) {
        AdminPO.RegResponsePO response = adminService.insertAdminMember(requestPO);
        return ApiResult.success(response);
    }

    @PutMapping(value = "/password-change")
    public ApiResult<AdminPO.PasswordResponsePO> passwordUpdateMember(@Valid @RequestBody AdminPO.PasswordRequestPO requestPO) {
        AdminPO.PasswordResponsePO response = adminService.updatePassword(requestPO);
        return ApiResult.success(response);
    }

    @PutMapping(value = "/password-reset")
    public ApiResult<AdminPO.ResetPasswordResponsePO> passwordResetMember(@Valid @RequestBody AdminPO.ResetPasswordRequestPO requestPO) {
        AdminPO.ResetPasswordResponsePO response = adminService.resetPassword(requestPO);
        return ApiResult.success(response);
    }
}
