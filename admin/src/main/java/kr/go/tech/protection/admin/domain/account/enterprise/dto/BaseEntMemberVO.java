package kr.go.tech.protection.admin.domain.account.enterprise.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
public class BaseEntMemberVO implements UserDetails {
    private Integer entMbrNo;         // 기업회원번호
    private String mbrSeCd;           // 회원구분 코드
    private String mbrDtlSeCd;        // 회원상세구분 코드
    private String jntCertInfo;       // 공동인증서 정보
    private String conmNm;             // 사업자명
    private String rprsvNm;           // 대표자명
    private String brNo;               // 사업자등록번호
    private String instSttsCd;        // 기관상태 코드
    private String bzmnTypeCd;        // 사업자유형 코드
    private String instTypeCd;        // 기관유형 코드
    private LocalDateTime bzEntFndnDt; // 사업체 설립일자
    private String rprsBzstatCd;      // 대표업태 코드
    private String rprsTpbizCd;       // 대표업종 코드
    private Integer empCnt;            // 직원 수
    private String fctYn;              // 공장 여부
    private String prvCorpSeCd;       // 개인법인 구분 코드
    private Long prvCorpNo;            // 개인법인 번호
    private String telNo;              // 기업 연락처
    private String bplcZip;            // 사업장 우편번호
    private String bplcRoadNm;        // 사업장 도로명
    private String bplcDaddr;         // 사업장 상세주소
    private String coHmpgAddr;        // 회사 홈페이지 주소
    private String mainPrdctn;        // 주요 생산 제품
    private String picNm;              // 담당자 이름
    private String picGndrCd;          // 담당자 성별 코드
    private LocalDateTime picBrdt;    // 담당자 출생일자
    private String picMblTelno;        // 담당자 휴대폰
    private String picSeCd;            // 담당자 구분 코드
    private String picDeptNm;          // 담당자 부서명
    private String picJbpsCd;          // 담당자 직위 코드
    private String emlAddr;            // 이메일 주소
    private String emlRcptnAgreYn;    // 이메일 수신 동의 여부

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
