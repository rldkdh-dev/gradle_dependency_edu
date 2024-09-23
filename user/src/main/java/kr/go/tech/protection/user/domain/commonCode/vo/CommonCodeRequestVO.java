package kr.go.tech.protection.user.domain.commonCode.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommonCodeRequestVO {

    @Getter
    @Setter
    @Builder
    public static class RequestList {
        private String groupCode;
        private CmnDtlCode cmnDtlCode;
    }

    @Getter
    @Setter
    @Builder
    public static class CmnDtlCode {
        private String dtlCode;
        private String dtlCodeName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RequestCodeVO {
        private String reqCode;
    }
}
