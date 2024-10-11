package kr.go.tech.protection.admin.domain.account.general.dto;

import java.time.LocalDateTime;
import java.util.List;
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
        private String genName;   //일반 회원명
        private String genId;      //일반회원 아이디
        private String genPhone;   //일반 회원 휴대폰 전화번호
        private String genEmail;   //일반회원 이메일
        private LocalDateTime createdAt;    //가입일
        private LocalDateTime modifiedAt;    //수정일
    }

    @Getter
    public static class SearchPO {
        private String conmNm;     //사업자명
        private String searchType;  //검색 조건
        private String searchKeyword;   //검색 단어
    }

    @Getter
    @Builder
    public static class DetailResponsePO{
        private String genName;     //이름  
        private String gender;      //성별
        private LocalDateTime birthDate;      //생년월일
        private String genId;      //일반회원 아이디
        private String genPhone;      //연락처
        private String genEmail;      //이메일
        private String isEmailConsent;      //이메일 수신 동의 여부
        private String address;      //주소

        //소속 기업정보
        private String companyName;      //사업장명
        private String businessRegistrationNumber;      //사업자 등록번호
        private String department;      //부서
        private String position;      //직위
        private String companyAddress;      //회사 주소
    }



/*
    @Getter
    @Setter
    public static class RegRequestPO {
        @NotBlank(message = "아이디는 null일 수 없습니다.")
        @Size(min=6, max=15, message = "아이디는 6자 이상 15자 이하이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9.]*$", message = "아이디는 영문으로 시작해야 하며, 영문, 숫자, 마침표만 사용가능하며 공백은 포함할 수 없습니다.")
        private String adminId;
        @NotNull(message = "이름은 null일 수 없습니다.")
        private String adminName;
        @NotNull(message = "내선 연락처는 null일 수 없습니다.")
        private String telNo;
        @NotNull(message = "개인 연락처는 null일 수 없습니다.")
        private String phoneNo;
        @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        @NotNull(message = "이메일은 null일 수 없습니다.")
        private String email;
        @NotNull(message = "그룹권한은 null일 수 없습니다.")
        private Integer authGroupNo;
    }

    @Getter
    @Builder
    public static class RegResponsePO {
        private Integer adminNo;
        private String adminId;
        private String adminName;
        private String telNo;
        private String phoneNo;
        private String email;
        private Integer authGroupNo;
        private String authGroupNm;
    }

    @Getter
    public static class PasswordRequestPO{
        @NotEmpty(message = "임시 비밀번호는 공백일 수 없습니다.")
        private String tempPassword;
        @NotEmpty(message = "비밀번호는 공백일 수 없습니다.")
        private String password;
        @NotEmpty(message = "비밀번호 확인은 공백일 수 없습니다.")
        private String passwordCheck;
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        private String adminId;
    }

    @Getter
    @Builder
    public static class PasswordResponsePO {
        private String adminId;
    }

    @Getter
    public static class ResetPasswordRequestPO {
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        private String adminId;
    }

    @Getter
    @Builder
    public static class ResetPasswordResponsePO {
        private String adminId;
        private Integer adminNo;
    }

    @Getter
    public static class UpdateRequestPO {
        @NotNull(message = "회원번호는 null일 수 없습니다.")
        private Integer adminNo;
        @NotEmpty(message = "아이디는 공백일 수 없습니다.")
        private String adminId;
        @NotEmpty(message = "이름은 공백일 수 없습니다.")
        @Size(min = 1, max = 100)
        private String adminName;
        @NotEmpty(message = "내선 연락처는 공백일 수 없습니다.")
        @Size(max = 11)
        private String telNo;
        @NotEmpty(message = "관리자 개인 연락처는 공백일 수 없습니다.")
        @Size(max = 11)
        private String phoneNo;
        @NotEmpty
        @Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotNull(message = "그룹번호는 null일 수 없습니다.")
        private Integer authGroupNo;
        @NotEmpty(message = "그룹명은 공백일 수 없습니다.")
        private String authGroupName;
    }

    @Getter
    @Builder
    public static class UpdateResponsePO {
        private Integer adminNo;
        private String adminId;
        private String adminName;
        private String telNo;
        private String phoneNo;
        private String email;
        private Integer authGroupNo;
        private String authGroupName;
    }*/
}
