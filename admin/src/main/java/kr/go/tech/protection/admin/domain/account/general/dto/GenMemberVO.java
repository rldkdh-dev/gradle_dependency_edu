package kr.go.tech.protection.admin.domain.account.general.dto;

import java.time.LocalDateTime;
import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Getter;

@Getter
public class GenMemberVO {

    @Getter
    public static class ListResponseVO extends BaseColumn{
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
    }


}
