package kr.go.tech.protection.user.global.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidationErrorResponse {
    private int status;
    private String message;
    private List<FieldError> fieldErrors;

    @Builder
    public ValidationErrorResponse(int status, String message, List<FieldError> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors != null ? fieldErrors : new ArrayList<>();
    }

    public static ValidationErrorResponse of(int status, String message, List<FieldError> fieldErrors) {
        return ValidationErrorResponse.builder()
                .status(status)
                .message(message)
                .fieldErrors(fieldErrors)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;

        @Builder
        public FieldError(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        public static FieldError of(String field, String message, Object rejectedValue) {
            return FieldError.builder()
                    .field(field)
                    .message(message)
                    .rejectedValue(rejectedValue)
                    .build();
        }
    }
}