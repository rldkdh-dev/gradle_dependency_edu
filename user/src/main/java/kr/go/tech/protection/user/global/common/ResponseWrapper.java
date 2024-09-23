package kr.go.tech.protection.user.global.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseWrapper {
    private int code;
    private String message;
    private Object data;
}
