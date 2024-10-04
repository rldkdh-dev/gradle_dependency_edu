package kr.go.tech.protection.admin.domain.login.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
        private String authGroupNo;
        private String authGroupName;
        private String adminId;
        private String adminName;
        private List<String> menuList;
    }
}
