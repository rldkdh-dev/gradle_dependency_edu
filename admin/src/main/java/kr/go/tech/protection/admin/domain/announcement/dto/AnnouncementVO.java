package kr.go.tech.protection.admin.domain.announcement.dto;

import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

public class AnnouncementVO {

    @Getter
    @Builder
    public static class InsertRequest extends BaseColumn {
        private String rcrtYr;
        private String rcrtFldCd;
        private Integer rcrtCycl;
        private Timestamp rcrtBgngDt;
        private Timestamp rcrtEndDt;
        private Timestamp entrstBgngDt;
        private Timestamp entrstEndDt;
        private String pstgYn;
    }
}
