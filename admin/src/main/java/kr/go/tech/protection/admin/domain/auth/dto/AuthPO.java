package kr.go.tech.protection.admin.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

public class AuthPO {

    @Getter
    @Builder
    public static class NameResponsePO {
        private Integer adminAuthGroupNo;
        private String adminAuthGroupName;
    }
}
