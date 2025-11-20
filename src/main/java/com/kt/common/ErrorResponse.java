package com.kt.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public static ResponseEntity<ErrorData> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ErrorData.of(status.series().name(), message));
    }

    @Data   // getter, setter, toString 등 자동 생성
    @AllArgsConstructor
    public static class ErrorData {
        private String code;    // 에러 코드 (예: "BAD_REQUEST")
        private String message; // 에러 메시지

        public static ErrorData of(String code, String message) {
            return new ErrorData(code, message);
        }
    }
}