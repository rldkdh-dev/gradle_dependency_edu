package kr.go.tech.protection.admin.domain.dept.controller;

import kr.go.tech.protection.admin.domain.dept.dto.DeptPO;
import kr.go.tech.protection.admin.domain.dept.service.DeptService;
import kr.go.tech.protection.admin.global.common.Department;
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
@RequestMapping("/api/v1/dept")
public class DeptController {

	private final DeptService deptService;
	private final Department department;

	@GetMapping("")
	public ApiResult<List<DeptPO.NameResponsePO>> selectDeptNameList() {
//		List<DeptPO.NameResponsePO> list = deptService.selectDeptNameList();
		List<DeptPO.NameResponsePO> list = department.deptArray;
		return ApiResult.success(list);
	}
}

