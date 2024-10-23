package kr.go.tech.protection.admin.domain.login.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class LoginPO {

    @Getter
    @Builder
    public static class LoginRequestPO {
        @NotEmpty
        private String adminId;
        @NotEmpty
        private String password;
    }

    @Getter
    @Builder
    public static class LoginResponsePO {
        private String accessToken;
        private Integer authGroupNo;
        private String authGroupName;
        private String adminId;
        private String adminName;
        private boolean passwordExpiredYn;
        private boolean tempPasswordYn;
    }
}
