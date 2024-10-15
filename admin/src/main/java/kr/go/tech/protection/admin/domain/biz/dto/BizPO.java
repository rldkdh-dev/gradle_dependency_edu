package kr.go.tech.protection.admin.domain.biz.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class BizPO {

    @Getter
    @Builder
    public static class InsertRequest {
        @NotNull
        private final Integer bizNo;

        @NotEmpty(message = "사업명을 입력하세요.")
        @Size(min = 1, max = 200, message = "1자에서 200자 사이로 입력하세요.")
        private final String bizName;

        @NotNull
        private final String adminId;

        @NotEmpty(message = "부서를 선택하세요.")
        private final Integer[] depts;

        @NotNull(message = "모집시작일을 입력하세요.")
        private Date recruitStartDt;

        @NotNull(message = "모집종료일을 입력하세요.")
        private Date recruitEndDt;
        
        @NotNull(message = "사업시작일을 입력하세요.")
        private Date bizStartDt;
        
        @NotNull(message = "사업종료일을 입력하세요.")
        private Date bizEndDt;

        @NotEmpty(message = "사업대상을 선택하세요.")
        private String[] target;

        @Size(max = 2000, message = "2000자 이내로 작성하세요.")
        private String bizContent;

        private String bizSummary;

        private String applicationProcess;

        private String contact;

        @NotEmpty(message = "진행 프로세스 블럭을 선택하세요.")
        private List<BizProcess> bizProcessJson;

        private List<Terms> terms;

        @NotEmpty(message = "신청서 양식을 작성하세요.")
        private List<BizForm> applicationFormJson;

        @NotNull
        private Integer tempSaveNo;
    }

    @Getter
    @Builder
    public static class BizProcess {
        private Integer order;
        private String processName;
        private String processCode;
        private Options options;
    }

    @Getter
    @Builder
    public static class Options {
        private String assessmentYn;
        private String expertType;

    }

    @Getter
    @Builder
    public static class Terms {
        private Integer sortNo;
        private String termsName;
        private String termsContent;
        private Boolean editYn;
    }

    @Getter
    @Builder
    public static class BizForm {
        private String componentId;
        private String title;
        private String type;
    }



    @Getter
    @Builder
    public static class InsertResponse {
        private final Integer bizNo;

        private final String bizName;

        private final String adminId;

        private final Integer[] depts;

        private Date recruitStartDt;

        private Date recruitEndDt;

        private Date bizStartDt;

        private Date bizEndDt;

        private String[] target;

        private String bizContent;

        private String fileName;

        private String bizSummary;

        private String applicationProcess;

        private String contact;

        private List<BizProcess> bizProcessJson;
//        private String bizProcessJson;

        private List<Terms> terms;

        private List<BizForm> applicationFormJson;
//        private String applicationFormJson;

    }
}
