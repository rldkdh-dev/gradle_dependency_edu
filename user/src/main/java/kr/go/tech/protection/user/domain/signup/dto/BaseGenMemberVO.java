package kr.go.tech.protection.user.domain.signup.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
public class BaseGenMemberVO implements UserDetails {
    private Integer mbrNo;  //회원번호
    private String linkInfo;  //연계정보
    private String mbrSeCd;  //회원 구분 코드
    private String mbrNm;  //회원명
    private String mbrGndrCd;  //회원 성별 코드
    private LocalDateTime mbrBrdt;  //회원 출생일자
    private String mbrMblTelno;  //회원 휴대전화 번호
    private String mbrId;  //회원 아이디
    private String mbrPswd;  //회원 비밀번호
    private LocalDateTime mbrPswdChgDt;  //회원 비밀번호 변경일
    private Integer cntnFailCnt;  //접속실패횟수
    private String emlAddr;  //이메일 주소
    private String homeZip;  //자택 우편번호
    private String homeRoadNm;  //자택 도로명
    private String homeDaddr;  //자택 상세주소
    private String emlRcptnAgreYn;  //이메일 수신 동의 여부

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
