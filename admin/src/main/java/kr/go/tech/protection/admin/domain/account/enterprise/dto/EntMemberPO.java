package kr.go.tech.protection.admin.domain.account.enterprise.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

public class EntMemberPO {

    @Getter
    @Builder
    public static class ListResponsePO {
        private Integer totalCount;
        private List<ListData> list;
    }

    @Getter
    @Builder
    public static class ListData {
        private Integer no; //번호
        private Integer entNo;   // 기업회원번호
        private String companyName; // 사업자명
        private String businessNumber;  // 사업자등록번호
        private String representativeName;  // 대표자명
        private String managerName; // 담당자명
        private String managerTelno;    // 담당자 연락처
        private String managerEmail;    // 담당자 이메일
        private String createdAt;    //가입일
    }

    @Getter
    @Builder
    public static class EmployeeListResponsePO {
        private Integer totalCount;
        private List<EmployeeListData> list;
    }

    @Getter
    @Builder
    public static class EmployeeListData {
        private Integer no; //번호
        private Integer genNo;   // 직원 일반 회원번호
        private String typeCode; // 구분 코드
        private String deptName; // 부서명
        private String positionCode; // 직위 코드
        private String name; // 이름
        private String telno; // 연락처
        private String email; // 구분 코드
    }

    @Getter
    public static class SearchPO {
        private String conmNm;     //사업자명
        private String brNo;     //사업자 등록번호
        private String searchType;  //검색 조건     CMR001 : 대표자명, CMR002 : 담당자명
        private String searchKeyword;   //검색 단어
    }

    @Getter
    public static class UpdateRequestPO {
        //기업 정보
        @NotNull(message = "기업회원번호는 null일 수 없습니다.")
        private Integer entNo;

        @NotNull(message = "사업자명은 null일 수 없습니다.")
        private String companyName;
        
        @NotNull(message = "대표자명은 null일 수 없습니다.")
        private String representativeName;
        
        @NotNull(message = "사업자등록번호는 null일 수 없습니다.")
        private String businessNumber;
        
        @NotNull(message = "사업자유형 코드는 null일 수 없습니다.")
        private String businessTypeCode;
        
        @NotNull(message = "기관유형 코드는 null일 수 없습니다.")
        private String institutionTypeCode;

        @NotNull(message = "대표업태 코드는 null일 수 없습니다.")
        private String representativeBusinessCode;

        @NotNull(message = "대표업종 코드는 null일 수 없습니다.")
        private String representativeIndustryCode;

        @NotNull(message = "직원수는 공백일 수 없습니다.")
        @Size(min = 1, message = "직원수는 1명 이상이여야 합니다.")
        private Integer employeeCount;

        @NotEmpty(message = "연락처는 공백일 수 없습니다.")
        @Size(max = 11, message = "연락처는 최대 11자리여야 합니다.")
        private String entTelNo;              // 기업 연락처

        @NotEmpty(message = "사업장주소는 공백일 수 없습니다.")
        private String companyAddress;       // 사업장 주소

        private String homepageUrl;        // 회사 홈페이지 주소
        private String mainProduct;        // 주요 생산 제품
        private String isFactory;              // 공장 여부

        //담당자 정보
        @NotNull(message = "담당자명은 null일 수 없습니다.")
        private String managerName;              // 담당자명

        @NotNull(message = "담당자 구분코드는 null일 수 없습니다.")
        private String managerTypeCode;            // 담당자 구분 코드

        private String managerDeptName;          // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String mangerTelno;        // 담당자 연락처
        private String managerEmail;            // 담당자 이메일
        private String isEmailConsent;    // 이메일 수신 동의 여부

    }

    @Getter
    @Builder
    public static class UpdateResponsePO {
        private Integer entNo;                      // 기업회원번호
        private String companyName;                  // 사업자명
        private String representativeName;           // 대표자명
        private String businessNumber;               // 사업자등록번호
        private String businessTypeCode;             // 사업자유형 코드
        private String institutionTypeCode;          // 기관유형 코드
        private String representativeBusinessCode;   // 대표업태 코드
        private String representativeIndustryCode;   // 대표업종 코드
        private Integer employeeCount;               // 직원수
        private String entTelNo;                     // 기업 연락처
        private String companyAddress;                // 사업장 주소
        private String homepageUrl;                   // 회사 홈페이지 주소
        private String mainProduct;                   // 주요 생산 제품
        private String isFactory;                     // 공장 여부

        // 담당자 정보
        private String managerName;                  // 담당자명
        private String managerTypeCode;              // 담당자 구분 코드
        private String managerDeptName;              // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String managerTelNo;                 // 담당자 연락처
        private String managerEmail;                  // 담당자 이메일
        private String isEmailConsent;               // 이메일 수신 동의 여부
    }

    @Getter
    public static class SearchBusinessNumberRequestPO {
        @NotNull(message = "사업자 등록번호를 입력하세요.")
        private String businessNumber;
    }

    @Getter
    @Builder
    public static class SearchBusinessNumberResponsePO {
        private Boolean isBusinessNumberAvailable;
    }

    @Getter
    @Builder
    public static class DetailResponsePO {
        private Integer entNo;                      // 기업회원번호
        private String companyName;                  // 사업자명
        private String representativeName;           // 대표자명
        private String businessNumber;               // 사업자등록번호
        private String businessTypeCode;             // 사업자유형 코드
        private String institutionTypeCode;          // 기관유형 코드
        private String representativeBusinessCode;   // 대표업태 코드
        private String representativeIndustryCode;   // 대표업종 코드
        private Integer employeeCount;               // 직원수
        private String entTelNo;                     // 기업 연락처
        private String companyAddress;                // 사업장 주소
        private String homepageUrl;                   // 회사 홈페이지 주소
        private String mainProduct;                   // 주요 생산 제품


        // 담당자 정보
        private String managerName;                  // 담당자명
        private String managerTypeCode;              // 담당자 구분 코드
        private String managerDeptName;              // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String managerTelNo;                 // 담당자 연락처
        private String managerEmail;                  // 담당자 이메일
        private String isEmailConsent;               // 이메일 수신 동의 여부

        //기술보호 수준 자가진단 결과
        private String sdgnNo;                      // 자가진단번호
        private String technicalProtectionTotalScore;    // 기술보호 수준 자가진단 결과 점수 (종합)
        private String institutionalProtectionScore; // 제도적 보호 관리 분야 점수
        private String personnelProtectionScore;    // 인적 보호 관리 분야 점수
        private String physicalProtectionScore;     // 물적 보호 관리 분야 점수
        private String accidentManagementScore;     // 사고재해 관리 분야 점수
        private String diagnosedAt;          // 자가 진단일
        private String expiryAt;                // 만료일
        private String isExpiration;                // 만료 여부

    }

    // 직원 목록 responseListData

    // 자가진단 수준 점수 data


}










