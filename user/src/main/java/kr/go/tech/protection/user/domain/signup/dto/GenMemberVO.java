package kr.go.tech.protection.user.domain.signup.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import kr.go.tech.protection.user.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GenMemberVO {

    @Getter
    public static class ListResponseVO extends BaseColumn {
        private Integer mbrNo;  //회원번호
        private String conmNm;   //소속기업명
        private String mbrNm;  //회원명
        private String mbrId;  //회원 아이디
        private String mbrMblTelno;  //회원 휴대전화 번호
        private String emlAddr;  //이메일 주소
    }

    @Getter
    public static class DefaultGenMemberVO extends BaseColumn {
        private Integer mbrNo;  //회원번호
        private String linkInfo;  //연계정보
        private String mbrSeCd;  //회원 구분 코드
        private String mbrNm;  //회원명
        private String mbrGndrCd;  //회원 성별 코드
        private String mbrBrdt;  //회원 출생일자
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
    }

    @Getter
    public static class DetailGenMemberVO extends BaseColumn {
        //회원 상세 정보
        private Integer mbrNo;  //회원번호
        private String linkInfo;  //연계정보
        private String mbrSeCd;  //회원 구분 코드
        private String mbrNm;  //회원명
        private String mbrGndrCd;  //회원 성별 코드
        private String mbrBrdt;  //회원 출생일자
        private String mbrMblTelno;  //회원 휴대전화 번호
        private String mbrId;  //회원 아이디
        private String mbrPswd;  //회원 비밀번호
        private LocalDateTime mbrPswdChgDt;  //회원 비밀번호 변경일
        private Integer cntnFailCnt;  //접속실패횟수
        private String emlAddr;  //이메일 주소
        private String emlRcptnAgreYn;  //이메일 수신 동의 여부
        private String address;  //주소

        private String homeZip;  //자택 우편번호
        private String homeRoadNm;  //자택 도로명
        private String homeDaddr;  //자택 상세주소

        //소속 기업 정보
        private Integer entMbrNo;  //기업회원번호
        private String seCd;  //구분 코드 ex: 대표자, 실무자
        private String jbpsCd;  //직위 코드
        private String deptNm;  //부서명
        private String conmNm;  //사업자명
        private String brNo;  //사업자 등록번호
        private String companyAddress;  //사업자 등록번호

        // 기업 소속 승인 여부
        private String alwYn;  //사업자 등록번호

        private String bplcZip;  //사업장 우편번호
        private String bplcRoadNm;  //사업장 도로명
        private String bplcDaddr;  //사업장 상세주소
    }

    @Getter
    @Builder
    public static class UpdateRequestVO extends BaseColumn{
        private Integer mbrNo;  //회원번호
        private String mbrNm;  //회원명
        private String mbrGndrCd;  //회원 성별 코드
        private Timestamp mbrBrdt;  //회원 출생일자
        private String mbrId;  //회원 아이디
        private String mbrMblTelno;  //회원 휴대전화 번호
        private String emlAddr;  //이메일 주소
        private String emlRcptnAgreYn;  //이메일 수신 동의 여부
        private String homeZip;  //자택 우편번호
        private String homeRoadNm;  //자택 도로명
        private String homeDaddr;  //자택 상세주소

    }

    @Getter
    @Builder
    public static class UpdateEntPrcptRequestVO extends BaseColumn {
        private Integer entMbrNo;  // 업데이트할 기업회원 번호
        private Integer mbrNo; // 일반회원 번호
    }

    @Getter
    @Builder
    public static class ResetPasswordRequestVO extends BaseColumn{
        private String mbrId;
        private String mbrPswd;
        private LocalDateTime mbrPswdChgDt;
    }

    @Getter
    @Builder
    public static class InsertRequestVO extends BaseColumn {
        private Integer mbrNo;  //회원번호
        private String mbrSeCd;  //회원 구분 코드
        private String mbrNm;  //회원명
        private String mbrGndrCd;  //회원 성별 코드
        private Timestamp mbrBrdt;  //회원 출생일자
        private String mbrMblTelno;  //회원 휴대전화 번호
        private String mbrId;  //회원 아이디
        private String mbrPswd;  //회원 비밀번호
        private Integer cntnFailCnt;  //접속실패횟수
        private String emlAddr;  //이메일 주소
        private String homeZip;  //자택 우편번호
        private String homeRoadNm;  //자택 도로명
        private String homeDaddr;  //자택 상세주소
        private String emlRcptnAgreYn;  //이메일 수신 동의 여부
    }

    @Getter
    @Builder
    public static class InsertEntPrcptVO extends BaseColumn {
        private Integer mbrNo;  //일반 회원 번호
        private Integer entMbrNo;  //기업 회원 번호
        private String seCd;  //구분 코든
        private String jbpsCd;  //직위 코드
        private String deptNm;  //부서명
    }



}
