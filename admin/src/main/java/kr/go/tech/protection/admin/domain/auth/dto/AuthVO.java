package kr.go.tech.protection.admin.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

public class AuthVO {

    @Getter
    @Builder
    public static class NameListResponseVO {
        private Integer authrtNo;
        private String authrtNm;
        private Integer menuNo;
        private Integer deptNo;
        private String menuCd;
        private String inqAuthrtYn;
        private String wholAuthrtYn;
    }
}
