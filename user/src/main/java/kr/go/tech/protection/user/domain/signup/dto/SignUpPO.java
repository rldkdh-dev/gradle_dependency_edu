package kr.go.tech.protection.user.domain.signup.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class SignUpPO {

    @Getter
    public static class GenSignUpRequestPO {
        @NotNull(message = "이름은 null일 수 없습니다.")
        private String genName;

        @NotNull(message = "성별코드는 null일 수 없습니다.")
        private String genderCd;

        @NotEmpty(message = "생년월일은 공백일 수 없습니다.")
        @Pattern(regexp = "\\d{8}", message = "생년월일은 yyyymmdd 형식이어야 합니다.") // 숫자 8자리
        private String birthDate;

        // 아이디: 영문 소문자로 시작, 6~15자, 숫자만 사용 불가
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        @Size(min = 6, max = 15, message = "아이디는 6자 이상, 15자 이하여야 합니다.")
        @Pattern(
            regexp = "^(?=.*[a-z])[a-z0-9]{6,15}$",
            message = "아이디는 영문 소문자로 시작하며, 숫자 조합이 가능하지만 숫자만 사용될 수 없습니다."
        )
        private String genId;

        // 비밀번호: 숫자, 영문 대소문자, 특수문자 포함 8~20자
        @NotEmpty(message = "비밀번호는 공백일 수 없습니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하여야 합니다.")
        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 조합하여 작성해야 합니다."
        )
        private String password;

        // 연락처: 11자리 숫자
        @NotEmpty(message = "연락처는 공백일 수 없습니다.")
        @Pattern(
            regexp = "^\\d{11}$",
            message = "연락처 형식이 올바르지 않습니다. (예: 01012345678)"
        )
        private String genPhone;

        @NotEmpty(message = "이메일은 공백일 수 없습니다.")
        @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다."
        )
        private String genEmail;

        @NotEmpty(message = "이메일 수신동의 여부는 null일 수 없습니다.")
        private String emailConsentYn;

        @NotEmpty(message = "우편번호는 null일 수 없습니다.")
        private String zipCode;

        @NotEmpty(message = "도로명은 null일 수 없습니다.")
        private String roadName;

        // 상세주소: 선택 입력 (null 허용)
        private String detailAddress;

        // 사업자등록번호: 선택 입력 (null 허용)
        private String businessNumber;
    }

    @Getter
    @Builder
    public static class GenSignUpResponsePO {
        private Integer genNo;
        private String genName;
        private String genderCd;
        private String birthDate;
        private String genId;
        private String genPhone;
        private String genEmail;
        private String emailConsentYn;
        private String zipCode;
        private String roadName;
        private String detailAddress; // 상세주소 (null 허용)
    }

    @Getter
    public static class EntSignUpRequestPO {
        @NotNull(message = "공동인증서정보는 null일 수 없습니다.")
        private String jointCertInfo; // 공동인증서 정보

        @NotNull(message = "사업자명은 null일 수 없습니다.")
        @Size(max = 30, message = "사업자명은 최대 30자까지 입력가능합니다.")
        private String companyName; // 사업자명

        @NotNull(message = "대표자명은 null일 수 없습니다.")
        @Size(max = 30, message = "대표자명은 최대 30자까지 입력가능합니다.")
        private String representativeName; // 대표자명

        @NotNull(message = "사업자등록번호는 null일 수 없습니다.")
        @Size(min = 10, max = 10, message = "사업자등록번호는 10자로 입력해주세요.")
        private String businessNumber; // 사업자등록번호

        @NotNull(message = "기관상태코드는 null일 수 없습니다.")
        private String institutionStatusCode; // 기관상태 코드

        @NotNull(message = "사업자유형 코드는 null일 수 없습니다.")
        private String businessTypeCode; // 사업자유형 코드

        @NotNull(message = "기관유형 코드는 null일 수 없습니다.")
        private String institutionTypeCode; // 기관유형 코드

        @NotNull(message = "기관유형 코드는 null일 수 없습니다.")
        private String enterpriseFoundationDate; // 사업체 설립일

        @NotNull(message = "대표업태 코드는 null일 수 없습니다.")
        private String representativeBusinessCode; // 대표업태 코드

        @NotNull(message = "대표업종 코드는 null일 수 없습니다.")
        private String representativeIndustryCode; // 대표업종 코드

        @NotNull(message = "직원수는 null일 수 없습니다.")
        @Min(value = 0, message = "직원수는 0 이상의 숫자여야 합니다.")
        private Integer employeeCount; // 직원수

        @NotNull(message = "공장여부는 null일 수 없습니다.")
        private String factoryYn; // 공장 여부

        private String IndividualCorporateSeparationCode; // 개인/법인 구분 코드

        private String IndividualCorporateNumber; // 개인/법인 번호

        // 연락처: 최대 11자리 숫자
        @Size(max = 11, message = "기업 연락처 형식이 올바르지 않습니다. (최대:11자리)")
        private String entTelNo; // 기업 연락처

        @NotNull(message = "사업장 주소는 null일 수 없습니다.")
        @Size(max = 100, message = "주소는 최대 100자까지 입력가능합니다.")
        private String companyAddress; // 사업장 주소

        @Size(max = 2048, message = "홈페이지 URL은 최대 2048자까지 입력가능합니다.")
        private String homepageUrl; // 회사 홈페이지 주소

        @Size(max = 100, message = "주요 생산품은 최대 100자까지 입력가능합니다.")
        private String mainProduct; // 주요 생산 제품

        // 담당자 정보
        @NotNull(message = "담당자명은 null일 수 없습니다.")
        @Size(max = 30, message = "담당자명은 최대 30자까지 입력가능합니다.")
        private String managerName; // 담당자명

        @NotNull(message = "담당자 성별은 null일 수 없습니다.")
        private String managerGenderCode; // 담당자 성별 코드

        @NotNull(message = "담당자 생년월일은 null일 수 없습니다.")
        private String managerBirthDate; // 담당자 생년월일

        // 연락처: 11자리 숫자
        @NotNull(message = "담당자 연락처는 null일 수 없습니다.")
        @Pattern(
            regexp = "^\\d{11}$",
            message = "연락처 형식이 올바르지 않습니다. (예: 01012345678)"
        )
        private String managerTelNo; // 담당자 연락처

        @NotNull(message = "담당자 구분 코드는 null일 수 없습니다.")
        private String managerTypeCode; // 담당자 구분 코드

        @NotNull(message = "담당자 부서명은 null일 수 없습니다.")
        @Size(max = 20, message = "부서명은 최대 20자까지 입력가능합니다.")
        private String managerDeptName; // 담당자 부서명

        @NotNull(message = "담당자 직위명은 null일 수 없습니다.")
        @Size(max = 20, message = "직위명은 최대 20자까지 입력가능합니다.")
        private String managerPositionCode; // 담당자 직위 코드

        @NotNull(message = "담당자 이메일은 null일 수 없습니다.")
        @Email(message = "이메일 양식에 맞춰 작성해주세요.")
        private String managerEmail; // 담당자 이메일

        @NotNull(message = "이메일 수신 동의 여부는 null일 수 없습니다.")
        private String emailConsentYn; // 이메일 수신 동의 여부
    }

    @Getter
    @Builder
    public static class EntSignUpResponsePO {
        private Integer entMbrNo;                  // 기업회원 번호
        private Integer jointCertInfo;                  // 공동인증서 정보
        private String companyName;                  // 사업자명
        private String representativeName;           // 대표자명
        private String businessNumber;               // 사업자등록번호
        private String institutionStatusCode;        // 기관상태 코드
        private String businessTypeCode;             // 사업자유형 코드
        private String institutionTypeCode;          // 기관유형 코드
        private String enterpriseFoundationDate;     // 사업체 설립일
        private String representativeBusinessCode;   // 대표업태 코드
        private String representativeIndustryCode;   // 대표업종 코드
        private Integer employeeCount;               // 직원수
        private String factoryYn;                     // 공장 여부
        private String IndividualCorporateSeparationCode; // 개인/법인 구분 코드
        private String IndividualCorporateNumber;     // 개인/법인 번호
        private String entTelNo;                     // 기업 연락처
        private String companyAddress;                // 사업장 주소
        private String homepageUrl;                   // 회사 홈페이지 주소
        private String mainProduct;                   // 주요 생산 제품

        // 담당자 정보
        private String managerName;                  // 담당자명
        private String managerGenderCode;           // 담당자 성별 코드
        private String managerBirthDate;            // 담당자 생년월일
        private String managerTelNo;                 // 담당자 연락처
        private String managerTypeCode;              // 담당자 구분 코드
        private String managerDeptName;              // 담당자 부서명
        private String managerPositionCode;          // 담당자 직위 코드
        private String managerEmail;                  // 담당자 이메일
        private String emailConsentYn;               // 이메일 수신 동의 여부
    }

    @Getter
    @AllArgsConstructor
    public static class PassAuthRequestPO {
        private String genName;                  // 이름
        private String genPhone;           // 전화번호
    }

    @Getter
    @AllArgsConstructor
    public static class JointCertInfoRequestPO {
        private String jointCertInfo;           // 공동인증서 정보
    }



/*    @Getter
    @Builder
    public static class ListResponsePO {

        private Integer totalCount;
        private List<ListData> list;
    }

    @Getter
    @Builder
    public static class ListData {
        private Integer no;         //번호
        private String companyName;    //소속기업명
        private Integer genNo;   //일반 회원번호
        private String genName;   //일반 회원명
        private String genId;      //일반회원 아이디
        private String genPhone;   //일반 회원 휴대폰 전화번호
        private String genEmail;   //일반회원 이메일
        private String createdAt;    //가입일
        private String modifiedAt;    //수정일
    }

    @Getter
    @AllArgsConstructor
    public static class SearchPO {
        private String companyName;     //사업자명
        private String searchType;  //검색 조건
        private String searchKeyword;   //검색 단어
    }

    @Getter
    @Builder
    public static class DetailResponsePO {
        private Integer genNo;   //일반 회원번호
        private String genName;     //이름
        private String genderCd;      //성별 코드
        private String birthDate;      //생년월일
        private String genId;      //일반회원 아이디
        private String genPhone;      //연락처
        private String genEmail;      //이메일
        private String emailConsentYn;      //이메일 수신 동의 여부
        private String address;      //주소

        private String zipCode;  //우편번호
        private String roadName;  //도로명
        private String detailAddress;  //상세주소

        //소속 기업정보
        private String companyName;      //사업장명
        private String businessNumber;      //사업자 등록번호
        private String department;      //부서
        private String position;      //직위
        private String companyAddress;      //회사 주소

        private String allowYn;  //기업소속 승인여부

        private String companyZipcode;  //사업장 우편번호
        private String companyRoadName;  //사업장 도로명
        private String companyDetailAddress;  //사업장 상세주소
    }


    @Getter
    public static class UpdateRequestPO {
        @NotNull(message = "회원번호는 null일 수 없습니다.")
        private Integer genNo;

        // 이름: 한글 또는 영문만 허용, 영문-한글 조합 불가
        @NotEmpty(message = "이름은 공백일 수 없습니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,50}$", message = "이름은 한글 또는 영문으로 작성해주세요.")
        private String genName;

        @NotNull(message = "성별코드는 null일 수 없습니다.")
        private String genderCd;

        // 생년월일: 8자리 숫자만 허용 (yyyymmdd 형식)
        @NotEmpty(message = "생년월일은 공백일 수 없습니다.")
        @Pattern(regexp = "\\d{8}", message = "생년월일은 yyyymmdd 형식이어야 합니다.")
        private String birthDate;

        // 아이디: 6~15자, 영문으로 시작, 영문 숫자 조합 가능, 마침표 사용 가능, 공백 불가
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        @Pattern(
            regexp = "^[a-zA-Z](?!.*(.)\\1{2})[a-zA-Z0-9.]{5,14}$",
            message = "아이디는 6~15자의 영문 또는 영문 숫자 조합으로 작성하고, 영문으로 시작해야 합니다."
        )
        private String genId;

        // 연락처: 11자리 숫자
        @NotEmpty(message = "연락처는 공백일 수 없습니다.")
        @Pattern(
            regexp = "^\\d{11}$",
            message = "연락처 형식이 올바르지 않습니다. (예: 01012345678)"
        )
        private String genPhone;

        // 이메일
        @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다."
        )
        private String genEmail;

        @NotEmpty(message = "이메일 수신동의 여부는 null일 수 없습니다.")
        private String emailConsentYn;

        @NotEmpty(message = "우편번호는 null일 수 없습니다.")
        private String zipCode;

        @NotEmpty(message = "도로명은 null일 수 없습니다.")
        private String roadName;

        // 상세주소: 선택 입력 (null 허용)
        private String detailAddress;

        // 사업자 등록번호: 10자리 숫자만 허용
        @Pattern(
            regexp = "^\\d{10}$",
            message = "사업자 등록번호는 10자리 숫자여야 합니다."
        )
        private String businessNumber;

    }

    @Getter
    @Builder
    public static class UpdateResponsePO {
        private Integer genNo;
        private String genName;
        private String genderCd;
        private String birthDate;
        private String genId;
        private String genPhone;
        private String genEmail;
        private String emailConsentYn;
        private String zipCode;
        private String roadName;
        private String detailAddress; // 상세주소 (null 허용)
    }

    @Getter
    public static class ResetPasswordRequestPO {
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        private String genId;
    }

    @Getter
    @Builder
    public static class ResetPasswordResponsePO {
        private String genId;
        private Integer genNo;
    }



    @Getter
    public static class SearchIdRequestPO {
        @NotNull(message = "아이디를 입력하세요.")
        private String searchId;
    }*/

}







