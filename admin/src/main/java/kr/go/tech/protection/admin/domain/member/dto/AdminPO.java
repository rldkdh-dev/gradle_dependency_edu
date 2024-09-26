package kr.go.tech.protection.admin.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class AdminPO {

    @Getter
    @Builder
    public static class ListResponsePO {
        private Integer totalCount;
        private List<ListData> list;
    }

    @Getter
    @Builder
    public static class ListData {
        private Integer no;
        private Integer adminNo;
        private String groupNm;
        private String id;
        private String name;
        private String phone;
        private String email;
        private LocalDateTime regDt;
        private LocalDateTime modDt;
    }

    @Getter
    @Setter
    public static class ResearchPO {
        private Integer authNo;
        private String searchType;
        private String searchKeyword;
    }
}
