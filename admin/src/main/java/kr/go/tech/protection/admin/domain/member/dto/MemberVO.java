package kr.go.tech.protection.admin.domain.member.dto;

import kr.go.tech.protection.admin.global.common.BaseColumn;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberVO {

    @Getter
    public static class ListResponseVO extends BaseColumn{
        private Integer mngrNo;
        private String mngrNm;
        private String mngrId;
        private String mngrPswd;
        private String tmprPswdYn;
        private String mngrTelno;
        private String mngrMblTelno;
        private String mngrEml;
        private String useYn;
        private Integer authrtNo;
        private String authrtNm;
    }

    @Getter
    public static class DefaultMemberVO extends BaseColumn {
        private Integer mngrNo;
        private String mngrNm;
        private String mngrId;
        private String mngrPswd;
        private String tmprPswdYn;
        private String mngrTelno;
        private String mngrMblTelno;
        private String mngrEml;
        private Integer authrtNo;
        private String authrtNm;
    }

    @Getter
    @Builder
    public static class RegRequestVO extends BaseColumn {
        private Integer authrtNo;
        private Integer mngrNo;
        private String mngrNm;
        private String mngrId;
        private String mngrPswd;
        private String tmprPswdYn;
        private String mngrTelNo;
        private String mngrMblTelNo;
        private String mngrEml;
        private String useYn;
    }

    @Getter
    @Builder
    public static class PasswordRequestVO extends BaseColumn{
        private String mngrId;
        private String mngrPswd;
        private String tmprPswdYn;
    }

    @Getter
    @Builder
    public static class ResetPasswordRequestVO extends BaseColumn{
        private String mngrId;
        private String mngrPswd;
        private String tmprPswdYn;
    }

    @Getter
    @Builder
    public static class UpdateRequestVO extends BaseColumn{
        private String mngrId;
        private String mngrNm;
        private String mngrTelno;
        private String mngrMblTelno;
        private String mngrEml;
        private Integer authrtNo;
    }
}
