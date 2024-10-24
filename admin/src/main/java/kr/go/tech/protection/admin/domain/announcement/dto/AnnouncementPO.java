package kr.go.tech.protection.admin.domain.announcement.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AnnouncementPO {
    
    @Getter
    @Builder
    public static class InsertResponse {
        private String recruitYear;
        private String recruitFiled;
        private Integer recruitCycle;
        private String recruitStartAt;
        private String recruitEndAt;
        private String entrustingStartAt;
        private String entrustingEndAt;
        private String postingYn;
    }

    @Getter
    @Builder
    public static class InsertRequest {
        @NotEmpty
        @Size(max = 4)
        @Pattern(regexp = "^(19|20)\\d{2}$", message = "유효한 년도를 입력하세요 (1900-2099).")
        private String recruitYear;
        @NotEmpty
        private String recruitFiled;
        @NotEmpty
        @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "날짜 형식으로 입력하세요(yyyy-mm-dd)")
        private String recruitStartAt;
        @NotEmpty
        @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "날짜 형식으로 입력하세요(yyyy-mm-dd)")
        private String recruitEndAt;
        @NotEmpty
        @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "날짜 형식으로 입력하세요(yyyy-mm-dd)")
        private String entrustingStartAt;
        @NotEmpty
        @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "날짜 형식으로 입력하세요(yyyy-mm-dd)")
        private String entrustingEndAt;
        @NotEmpty
        private String postingYn;

    }
}
