package kr.go.tech.protection.admin.domain.member.dto;

import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Getter;

@Getter
public class AdminVO extends BaseColumn {

    @Getter
    public static class ListResponseVO extends BaseColumn{
        private Integer mngrNo;
        private Integer deptNo;
        private String deptNm;
        private String mngrNm;
        private String mngrId;
        private String mngrPswd;
        private String mngrTmprPswd;
        private String tmprPswdYn;
        private String mngrTelno;
        private String mngrMblTelno;
        private String mngrEml;
        private String useYn;
    }

    @Getter
    public static class MemberVO extends BaseColumn {
        private Integer mngrNo;
        private Integer deptNo;
        private String deptNm;
        private String mngrNm;
        private String mngrId;
        private String mngrPswd;
        private String mngrTmprPswd;
        private String tmprPswdYn;
        private String mngrTelno;
        private String mngrMblTelno;
        private String mngrEml;
        private String useYn;
    }
}
