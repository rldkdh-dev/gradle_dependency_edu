package kr.go.tech.protection.admin.domain.dept.dto;

import lombok.Builder;
import lombok.Getter;

public class DeptVO {

    @Getter
    @Builder
    public static class NameListResponseVO {
        private Integer authrtNo;
        private String authrtNm;
    }
}
