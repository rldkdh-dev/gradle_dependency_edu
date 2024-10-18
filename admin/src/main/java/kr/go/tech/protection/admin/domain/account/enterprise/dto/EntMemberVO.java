package kr.go.tech.protection.admin.domain.account.enterprise.dto;

import java.time.LocalDateTime;
import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EntMemberVO {

    @Getter
    public static class ListResponseVO extends BaseColumn{
        private Integer entMbrNo; // 기업회원번호
        private String conmNm;             // 사업자명
        private String brNo;               // 사업자등록번호
        private String rprsvNm;           // 대표자명
        private String picNm;              // 담당자명
        private String picMblTelno;        // 담당자 연락처
        private String emlAddr;            // 담당자 이메일
    }

    @Getter
    public static class EmployeeListResponseVO extends BaseColumn{
        private Integer mbrNo;           // 직원 일반 회원번호
        private String seCd;              // 구분 코드
        private String deptNm;        // 부서명
        private String jbpsCd;            // 직위 코드
        private String mbrNm;             // 이름
        private String mbrMblTelno;       // 연락처
        private String emlAddr;           // 이메일
    }

    @Getter
    public static class DefaultEntMemberVO extends BaseColumn {
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
        private String bplcAddress;         // 사업장 상세주소
        private String coHmpgAddr;        // 회사 홈페이지 주소
        private String mainPrdctn;        // 주요 생산 제품
        private String picNm;              // 담당자명
        private String picGndrCd;          // 담당자 성별 코드
        private LocalDateTime picBrdt;    // 담당자 출생일자
        private String picMblTelno;        // 담당자 연락처
        private String picSeCd;            // 담당자 구분 코드
        private String picDeptNm;          // 담당자 부서명
        private String picJbpsCd;          // 담당자 직위 코드
        private String emlAddr;            // 담당자 이메일
        private String emlRcptnAgreYn;    // 이메일 수신 동의 여부
    }

    @Getter
    public static class DetailEntMemberVO extends BaseColumn {
        private Integer entMbrNo;         // 기업회원번호
        private String mbrSeCd;           // 회원구분 코드
        private String mbrDtlSeCd;        // 회원상세구분 코드
        private String jntCertInfo;       // 공동인증서 정보
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;               // 사업자등록번호
        private String bzmnTypeCd;        // 사업자유형 코드
        private String instTypeCd;        // 기관유형 코드
        private String rprsBzstatCd;      // 대표업태 코드
        private String rprsTpbizCd;       // 대표업종 코드
        private Integer empCnt;            // 직원 수
        private String telNo;              // 기업 연락처
        private String bplcAddress;            // 사업장 주소
        private String bplcZip;            // 사업장 우편번호
        private String bplcRoadNm;        // 사업장 도로명
        private String bplcDaddr;         // 사업장 상세주소
        private String coHmpgAddr;        // 회사 홈페이지 주소
        private String mainPrdctn;        // 주요 생산 제품

        private String picNm;              // 담당자명
        private String picSeCd;            // 담당자 구분 코드
        private String picDeptNm;          // 담당자 부서명
        private String picJbpsCd;          // 담당자 직위 코드
        private String picMblTelno;        // 담당자 연락처
        private String emlAddr;            // 담당자 이메일
        private String emlRcptnAgreYn;    // 이메일 수신 동의 여부

        //기술보호 수준 자가진단 결과
        private String sdgnNo;    // 자가진단번호
        private String totalScore;    // 기술보호 수준 자가진단 결과 점수 (종합)
        private String instPrtcMngFldScr;    // 제도적 보호 관리 분야 점수
        private String psnlPrtcngFldScr;    // 인적 보호 관리 분야 점수
        private String physPrtcMngFldScr;    // 물적 보호 관리 분야 점수
        private String acdntDisMngFldScr;    // 사고재해 관리 분야 점수
        private LocalDateTime expirationDate;    // 만료일
        private String expirationYn;    // 만료 여부

	}

    @Getter
    @Builder
    public static class UpdateRequestVO extends BaseColumn{
        //기업 정보
        private Integer entMbrNo;         // 기업회원번호
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;               // 사업자등록번호
        private String bzmnTypeCd;        // 사업자유형 코드
        private String instTypeCd;        // 기관유형 코드
        private String rprsBzstatCd;      // 대표업태 코드
        private String rprsTpbizCd;       // 대표업종 코드
        private Integer empCnt;            // 직원 수
        private String telNo;              // 기업 연락처

        private String bplcZip;            // 사업장 우편번호
        private String bplcRoadNm;        // 사업장 도로명
        private String bplcDaddr;         // 사업장 상세주소

        private String coHmpgAddr;        // 회사 홈페이지 주소
        private String mainPrdctn;        // 주요 생산 제품
        private String fctYn;              // 공장 여부
        
        //담당자 정보
        private String picNm;              // 담당자명
        private String picSeCd;            // 담당자 구분 코드
        private String picDeptNm;          // 담당자 부서명
        private String picJbpsCd;          // 담당자 직위 코드
        private String picMblTelno;        // 담당자 연락처
        private String emlAddr;            // 담당자 이메일
        private String emlRcptnAgreYn;    // 이메일 수신 동의 여부
    }

    @Getter
    @Builder
    public static class UpdateEntPrcptRequestVO extends BaseColumn {
        private String entMbrNo;  // 업데이트할 기업회원 번호
        private Integer mbrNo; // 일반회원 번호
    }

    @Getter
    @Builder
    public static class InsertRequestVO extends BaseColumn {
        //기업 정보
        private Integer entMbrNo;         // 기업회원번호
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;               // 사업자등록번호
        private String bzmnTypeCd;        // 사업자유형 코드
        private String instTypeCd;        // 기관유형 코드
        private String rprsBzstatCd;      // 대표업태 코드
        private String rprsTpbizCd;       // 대표업종 코드
        private Integer empCnt;            // 직원 수
        private String telNo;              // 기업 연락처

        private String bplcZip;            // 사업장 우편번호
        private String bplcRoadNm;        // 사업장 도로명
        private String bplcDaddr;         // 사업장 상세주소

        private String coHmpgAddr;        // 회사 홈페이지 주소
        private String mainPrdctn;        // 주요 생산 제품
        private String fctYn;              // 공장 여부

        //담당자 정보
        private String picNm;              // 담당자명
        private String picSeCd;            // 담당자 구분 코드
        private String picDeptNm;          // 담당자 부서명
        private String picJbpsCd;          // 담당자 직위 코드
        private String picMblTelno;        // 담당자 연락처
        private String emlAddr;            // 담당자 이메일
        private String emlRcptnAgreYn;    // 이메일 수신 동의 여부
    }



}
