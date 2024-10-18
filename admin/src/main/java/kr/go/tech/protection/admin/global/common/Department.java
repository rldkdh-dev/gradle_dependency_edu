package kr.go.tech.protection.admin.global.common;

import kr.go.tech.protection.admin.domain.dept.dto.DeptPO;
import kr.go.tech.protection.admin.domain.dept.service.DeptInitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Department {

    private final DeptInitService deptInitService;
    public List<DeptPO.NameResponsePO> deptArray = new ArrayList<>();

    @PostConstruct
    public void init() {
        deptArray = deptInitService.selectDeptNameList();
        log.info("///////////// DEPARTMENT INIT //////////////");
    }

    public void syncDepartment() {
        deptArray.clear();
        deptInitService.selectDeptNameList().forEach(dept->
                deptArray.add(
                        DeptPO.NameResponsePO.builder()
                                .adminDepartmentNo(dept.getAdminDepartmentNo())
                                .adminDepartmentName(dept.getAdminDepartmentName()).build()
                ));
    }
}
