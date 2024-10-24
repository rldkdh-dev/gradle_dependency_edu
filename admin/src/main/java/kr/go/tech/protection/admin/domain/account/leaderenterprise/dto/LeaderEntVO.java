package kr.go.tech.protection.admin.domain.account.leaderenterprise.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LeaderEntVO {

    @Getter
    public static class ListResponseVO extends BaseColumn{
        private Integer ldrEntNo;   // 선도기업번호
        private Integer entMbrNo;   // 기업회원번호
        private String ldrEntSeCd;  // 선도기업 분류코드
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;     //사업자 등록번호
        private LocalDateTime bgngDt;      // 시작일시
        private LocalDateTime endDt;       // 종료일시
        private Integer lvlIdntyScr; // 수준확인점수
        private String dsgnNo;      // 지정 번호
        private String expirationYn;      // 만료 여부
    }

    @Getter
    public static class DefaultEntMemberVO extends BaseColumn {
        private Integer ldrEntNo;   // 선도기업번호
        private Integer entMbrNo;   // 기업회원번호
        private String ldrEntSeCd;  // 선도기업 분류코드
        private LocalDateTime bgngDt;      // 시작일시
        private LocalDateTime endDt;       // 종료일시
        private Integer lvlIdntyScr; // 수준확인점수
        private String dsgnNo;      // 지정 번호
    }

    @Getter
    public static class DetailEntMemberVO extends BaseColumn {
        // 선도기업 정보
        private Integer ldrEntNo;   // 선도기업번호
        private String ldrEntSeCd;  // 선도기업 분류코드
        private LocalDateTime bgngDt;      // 시작일시
        private LocalDateTime endDt;       // 종료일시
        private Integer lvlIdntyScr; // 수준확인점수
        private String dsgnNo;      // 지정 번호
        private String expirationYn;      // 만료 여부

        // 기업 정보
        private Integer entMbrNo;   // 기업회원번호
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;     //사업자 등록번호
	}

    @Getter
    public static class EntInfoResponseVO extends BaseColumn {
        private Integer entMbrNo;   // 기업회원번호
        private String conmNm;             // 사업자명
        private String rprsvNm;           // 대표자명
        private String brNo;     //사업자 등록번호
	}

    @Getter
    @Builder
    public static class UpdateRequestVO extends BaseColumn{
        private Integer ldrEntNo;   // 선도기업번호
        private Integer entMbrNo;   // 기업회원번호
        private String ldrEntSeCd;  // 선도기업 분류코드
        private LocalDateTime bgngDt;      // 시작일시
        private LocalDateTime endDt;       // 종료일시
        private Integer lvlIdntyScr; // 수준확인점수
        private String dsgnNo;      // 지정 번호
    }

    @Getter
    @Builder
    public static class InsertRequestVO extends BaseColumn {
        private Integer ldrEntNo;   // 선도기업번호
        private Integer entMbrNo;   // 기업회원번호
        private String ldrEntSeCd;  // 선도기업 분류코드
        private LocalDateTime bgngDt;      // 시작일시
        private LocalDateTime endDt;       // 종료일시
        private Integer lvlIdntyScr; // 수준확인점수
        private String dsgnNo;      // 지정 번호
    }



}
