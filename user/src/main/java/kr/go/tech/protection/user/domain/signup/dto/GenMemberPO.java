package kr.go.tech.protection.user.domain.signup.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GenMemberPO {

    @Getter
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
    public static class InsertRequestPO {
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

        @NotEmpty(message = "사업자 등록번호는 null일 수 없습니다.")
        @Pattern(regexp = "\\d{10}", message = "사업자 등록번호는 10자리 숫자여야 합니다.")
        private String businessNumber;
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
    @Builder
    public static class InsertResponsePO {
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
    public static class SearchIdRequestPO {
        @NotNull(message = "아이디를 입력하세요.")
        private String searchId;
    }

}







