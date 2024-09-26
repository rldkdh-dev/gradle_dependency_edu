package kr.go.tech.protection.admin.global.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseColumn {
    protected LocalDateTime frstRegDt;
    protected String frstRgtrId;
    protected LocalDateTime lastMdfcnDt;
    protected String lastMdfrId;

    public void setFirst(String objName) {
        this.frstRegDt = LocalDateTime.now();
        this.frstRgtrId = objName;
        this.lastMdfcnDt = LocalDateTime.now();
        this.lastMdfrId = objName;
    }

    public void setLast(String objName) {
        this.lastMdfcnDt = LocalDateTime.now();
        this.lastMdfrId = objName;
    }
}
