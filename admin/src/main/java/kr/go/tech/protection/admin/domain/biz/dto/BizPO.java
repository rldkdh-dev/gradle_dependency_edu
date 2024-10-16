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
        private final Integer pageNo;

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

        private String bizSummary;

        private String applicationProcess;

        private String contact;

        private List<BizProcess> bizProcessJson;

        private List<Terms> terms;

        private List<BizForm> applicationFormJson;
    }

    @Getter
    @Builder
    public static class TempInsertRequest {
        @NotNull
        private final Integer pageNo;

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

        private String bizSummary;

        private String applicationProcess;

        private String contact;

        private List<BizProcess> bizProcessJson;

        private List<Terms> terms;

        private List<BizForm> applicationFormJson;

        private String tempTitle;
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

        private Integer pageNo;
    }
}
