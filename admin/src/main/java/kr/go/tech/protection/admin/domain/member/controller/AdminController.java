package kr.go.tech.protection.admin.domain.member.controller;

import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.domain.member.service.AdminService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/member")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/list")
    public ApiResult<AdminPO.ListResponsePO> selectAdminMemberList(AdminPO.SearchPO searchPO){
        AdminPO.ListResponsePO data = adminService.selectAdminMemberList(searchPO);

        return ApiResult.success(data);
    }

    @GetMapping("/{no}")
    public ApiResult<AdminPO.DetailResponsePO> selectAdminMemberByNo(@PathVariable int no) {
        AdminPO.DetailResponsePO data = adminService.selectAdminMemberByNo(no);
        return ApiResult.success(data);
    }
}
