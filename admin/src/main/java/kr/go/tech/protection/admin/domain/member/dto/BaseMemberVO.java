package kr.go.tech.protection.admin.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class BaseMemberVO implements UserDetails {
    private Integer mngrNo;
    private String mngrNm;
    private String mngrId;
    private String mngrPswd;
    private String tmprPswdYn;
    private String mngrTelno;
    private String mngrMblTelno;
    private String mngrEml;
    private String useYn;
    private Integer authrtGroupNo;
    private String authrtGroupNm;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
