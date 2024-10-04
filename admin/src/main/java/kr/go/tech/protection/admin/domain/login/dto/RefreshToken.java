package kr.go.tech.protection.admin.domain.login.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public class RefreshToken {
    @Id
    private String refreshToken;

    private String loginId;

//    @TimeToLive
    Integer expiration;

    public void updateToken(String newToken) {
        this.refreshToken = newToken;
    }
}
