package kr.go.tech.protection.admin.domain.dept.dto;

import lombok.Builder;
import lombok.Getter;

public class DeptPO {

    @Getter
    @Builder
    public static class NameResponsePO {
        private Integer adminDepartmentNo;
        private String adminDepartmentName;
    }
}
