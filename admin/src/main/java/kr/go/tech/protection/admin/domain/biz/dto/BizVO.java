package kr.go.tech.protection.admin.domain.biz.dto;

import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

public class BizVO {

    @Getter
    @Builder
    public static class DefaultBiz extends BaseColumn {
        private Integer pageNo;
        private Integer bizNo;
        private Integer fileNo;
        private String bizNm;
        private String tkcgDeptCd;
        private String picId;
        private LocalDateTime rcrtBgngDt;
        private LocalDateTime rcrtEndDt;
        private LocalDateTime bizBgngDt;
        private LocalDateTime bizEndDt;
        private String bizTrgtCd;
        private String bizCn;
        private String rcrtSttsCd;
        private String bizSttsCd;
        private String bizSmrCn;
        private String aplyPrcCn;
        private String cntInfCn;
        private String prgrsPrcsJsn;
        private String aplyFrmJsn;
        private String strgYn;
        private String tmprStrgYn;
    }

    @Getter
    @Builder
    public static class InsertTerm extends BaseColumn {
        private Integer bizNo;
        private String trmsNm;
        private String trmsCn;
        private Integer sortNo;
    }

    @Getter
    @Builder
    public static class DefaultTempSave extends BaseColumn {
        private Integer tmprStrgNo;
        private Integer bizNo;
        private String tmprTtl;
    }

    @Getter
    @Builder
    public static class ListResponse {
        private Integer bizNo;
        private String bizNm;
        private String mngrNm;
        private String deptNm;
        private Date rcrtBgngDt;
        private Date rcrtEndDt;
        private Date bizBgngDt;
        private Date bizEndDt;
        private String bizSttsCd;
        private Integer applicantsCount;
    }

    @Getter
    @Builder
    public static class UpdateBiz extends BaseColumn{
        private String afterBizStartDt;
        private Integer pageNo;
        private Integer bizNo;
        private String bizNm;
        private String tkcgDeptCd;
        private LocalDateTime rcrtBgngDt;
        private LocalDateTime rcrtEndDt;
        private LocalDateTime bizBgngDt;
        private LocalDateTime bizEndDt;
        private String bizTrgtCd;
        private String bizCn;
        private String rcrtSttsCd;
        private String bizSttsCd;
        private String bizSmrCn;
        private String aplyPrcCn;
        private String cntInfCn;
        private String prgrsPrcsJsn;
        private String aplyFrmJsn;
    }

    @Getter
    @Builder
    public static class BizDetail {
        private Integer bizNo;
        private String bizNm;
        private String picId;
        private String mngrNm;
        private LocalDateTime rcrtBgngDt;
        private LocalDateTime rcrtEndDt;
        private LocalDateTime bizBgngDt;
        private LocalDateTime bizEndDt;
    }
}
