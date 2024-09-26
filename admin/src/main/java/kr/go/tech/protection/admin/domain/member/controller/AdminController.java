package kr.go.tech.protection.admin.domain.member.controller;

import kr.go.tech.protection.admin.domain.member.service.AdminService;
import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/member")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/list")
    public ApiResult<AdminPO.ListResponsePO> selectAdminMemberList(AdminPO.ResearchPO researchPO){
        AdminPO.ListResponsePO data = adminService.selectAdminMemberList(researchPO);

        return ApiResult.success(data);
    }


}
