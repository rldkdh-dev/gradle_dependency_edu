package kr.go.tech.protection.user.domain.signup.dto;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
    @AllArgsConstructor
    public static class SearchPO {
        private String companyName;     //사업자명
        private String businessNumber;     //사업자 등록번호
        private String searchType;  //검색 조건     CMR001 : 대표자명, CMR002 : 담당자명
        private String searchKeyword;   //검색 단어
    }

    @Getter
    public static class UpdateRequestPO {
        //기업 정보
        @NotNull(message = "기업회원번호는 null일 수 없습니다.")
        private Integer entNo;

        @NotNull(message = "사업자명은 null일 수 없습니다.")
        @Size(max = 30, message = "사업자명은 최대 30자까지 입력가능합니다.")
        private String companyName; // 사업자명

        @NotNull(message = "대표자명은 null일 수 없습니다.")
        @Size(max = 30, message = "대표자명은 최대 30자까지 입력가능합니다.")
        private String representativeName; // 대표자명

        @NotNull(message = "사업자등록번호는 null일 수 없습니다.")
        @Size(min = 10, max = 10, message = "사업자등록번호는 10자로 입력해주세요.")
        private String businessNumber; // 사업자등록번호

        @NotNull(message = "사업자유형 코드는 null일 수 없습니다.")
        private String businessTypeCode; // 사업자유형 코드

        @NotNull(message = "기관유형 코드는 null일 수 없습니다.")
        private String institutionTypeCode; // 기관유형 코드

        @NotNull(message = "대표업태 코드는 null일 수 없습니다.")
        private String representativeBusinessCode; // 대표업태 코드

        @NotNull(message = "대표업종 코드는 null일 수 없습니다.")
        private String representativeIndustryCode; // 대표업종 코드

        @NotNull(message = "직원수는 null일 수 없습니다.")
        @Min(value = 0, message = "직원수는 0 이상의 숫자여야 합니다.")
        private Integer employeeCount; // 직원수

        @NotNull(message = "기업 연락처는 null일 수 없습니다.")
        private String entTelNo; // 기업 연락처

        @NotNull(message = "사업장 주소는 null일 수 없습니다.")
        @Size(max = 100, message = "주소는 최대 100자까지 입력가능합니다.")
        private String companyAddress; // 사업장 주소

        @Size(max = 2048, message = "홈페이지 URL은 최대 2048자까지 입력가능합니다.")
        private String homepageUrl; // 회사 홈페이지 주소

        @Size(max = 100, message = "주요 생산품은 최대 100자까지 입력가능합니다.")
        private String mainProduct; // 주요 생산 제품

        @NotNull(message = "공장여부는 null일 수 없습니다.")
        private String factoryYn; // 공장 여부

        // 담당자 정보
        @NotNull(message = "담당자명은 null일 수 없습니다.")
        @Size(max = 30, message = "담당자명은 최대 30자까지 입력가능합니다.")
        private String managerName; // 담당자명

        @NotNull(message = "담당자 구분 코드는 null일 수 없습니다.")
        private String managerTypeCode; // 담당자 구분 코드

        @Size(max = 20, message = "부서명은 최대 20자까지 입력가능합니다.")
        private String managerDeptName; // 담당자 부서명

        @Size(max = 20, message = "직위명은 최대 20자까지 입력가능합니다.")
        private String managerPositionCode; // 담당자 직위 코드

        @NotNull(message = "담당자 연락처는 null일 수 없습니다.")
        private String managerTelNo; // 담당자 연락처

        @NotNull(message = "담당자 이메일은 null일 수 없습니다.")
        @Email(message = "이메일 양식에 맞춰 작성해주세요.")
        private String managerEmail; // 담당자 이메일

        @NotNull(message = "이메일 수신 동의 여부는 null일 수 없습니다.")
        private String emailConsentYn; // 이메일 수신 동의 여부
    }

    @Getter
    public static class InsertRequestPO {
        @NotNull(message = "사업자명은 null일 수 없습니다.")
        @Size(max = 30, message = "사업자명은 최대 30자까지 입력가능합니다.")
        private String companyName; // 사업자명

        @NotNull(message = "대표자명은 null일 수 없습니다.")
        @Size(max = 30, message = "대표자명은 최대 30자까지 입력가능합니다.")
        private String representativeName; // 대표자명

        @NotNull(message = "사업자등록번호는 null일 수 없습니다.")
        @Size(min = 10, max = 10, message = "사업자등록번호는 10자로 입력해주세요.")
        private String businessNumber; // 사업자등록번호

        @NotNull(message = "사업자유형 코드는 null일 수 없습니다.")
        private String businessTypeCode; // 사업자유형 코드

        @NotNull(message = "기관유형 코드는 null일 수 없습니다.")
        private String institutionTypeCode; // 기관유형 코드

        @NotNull(message = "대표업태 코드는 null일 수 없습니다.")
        private String representativeBusinessCode; // 대표업태 코드

        @NotNull(message = "대표업종 코드는 null일 수 없습니다.")
        private String representativeIndustryCode; // 대표업종 코드

        @NotNull(message = "직원수는 null일 수 없습니다.")
        @Min(value = 0, message = "직원수는 0 이상의 숫자여야 합니다.")
        private Integer employeeCount; // 직원수

        @NotNull(message = "기업 연락처는 null일 수 없습니다.")
        private String entTelNo; // 기업 연락처

        @NotNull(message = "사업장 주소는 null일 수 없습니다.")
        @Size(max = 100, message = "주소는 최대 100자까지 입력가능합니다.")
        private String companyAddress; // 사업장 주소

        @Size(max = 2048, message = "홈페이지 URL은 최대 2048자까지 입력가능합니다.")
        private String homepageUrl; // 회사 홈페이지 주소

        @Size(max = 100, message = "주요 생산품은 최대 100자까지 입력가능합니다.")
        private String mainProduct; // 주요 생산 제품

        @NotNull(message = "공장여부는 null일 수 없습니다.")
        private String factoryYn; // 공장 여부

        // 담당자 정보
        @NotNull(message = "담당자명은 null일 수 없습니다.")
        @Size(max = 30, message = "담당자명은 최대 30자까지 입력가능합니다.")
        private String managerName; // 담당자명

        @NotNull(message = "담당자 구분 코드는 null일 수 없습니다.")
        private String managerTypeCode; // 담당자 구분 코드

        @Size(max = 20, message = "부서명은 최대 20자까지 입력가능합니다.")
        private String managerDeptName; // 담당자 부서명

        @Size(max = 20, message = "직위명은 최대 20자까지 입력가능합니다.")
        private String managerPositionCode; // 담당자 직위 코드

        @NotNull(message = "담당자 연락처는 null일 수 없습니다.")
        private String managerTelNo; // 담당자 연락처

        @NotNull(message = "담당자 이메일은 null일 수 없습니다.")
        @Email(message = "이메일 양식에 맞춰 작성해주세요.")
        private String managerEmail; // 담당자 이메일

        @NotNull(message = "이메일 수신 동의 여부는 null일 수 없습니다.")
        private String emailConsentYn; // 이메일 수신 동의 여부
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
        private String factoryYn;                     // 공장 여부

        // 담당자 정보
        private String managerName;                  // 담당자명
        private String managerTypeCode;              // 담당자 구분 코드
        private String managerDeptName;              // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String managerTelNo;                 // 담당자 연락처
        private String managerEmail;                  // 담당자 이메일
        private String emailConsentYn;               // 이메일 수신 동의 여부
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
        private String emailConsentYn;               // 이메일 수신 동의 여부

        //기술보호 수준 자가진단 결과
        private String sdgnNo;                      // 자가진단번호
        private String technicalProtectionTotalScore;    // 기술보호 수준 자가진단 결과 점수 (종합)
        private String institutionalProtectionScore; // 제도적 보호 관리 분야 점수
        private String personnelProtectionScore;    // 인적 보호 관리 분야 점수
        private String physicalProtectionScore;     // 물적 보호 관리 분야 점수
        private String accidentManagementScore;     // 사고재해 관리 분야 점수
        private String diagnosedAt;          // 자가 진단일
        private String expiryAt;                // 만료일
        private String ExpirationYn;                // 만료 여부
    }

    @Getter
    @Builder
    public static class InsertResponsePO {
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
        private String factoryYn;                     // 공장 여부

        // 담당자 정보
        private String managerName;                  // 담당자명
        private String managerTypeCode;              // 담당자 구분 코드
        private String managerDeptName;              // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String managerTelNo;                 // 담당자 연락처
        private String managerEmail;                  // 담당자 이메일
        private String emailConsentYn;               // 이메일 수신 동의 여부
    }


}










