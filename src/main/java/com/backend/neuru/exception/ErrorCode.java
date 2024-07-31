package com.backend.neuru.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "회원가입하지 않은 사용자입니다."),
    PASSWORD_IS_NOT_CORRECT(HttpStatus.UNAUTHORIZED,"ACCOUNT-004","비밀번호가 불일치합니다."),
    DUPLICATE_FIX_SEAT(HttpStatus.CONFLICT,"DUPLICATE-FIXSEAT","고정 좌석이 중복됩니다."),
    DUPLICATE_USER_NAME(HttpStatus.FORBIDDEN, "DUPLICATE-USERNAME","같은 이름이 존재합니다."),
    WALKWAY_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "WALKWAY_LIKE_NOT_FOUND", "해당 산책로의 좋아요가 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글이 존재하지 않습니다."),
    WALKWAY_JSON_NOT_FOUND(HttpStatus.NOT_FOUND, "WALKWAY_JSON_NOT_FOUND", "해당 walkway.json not found."),
    CITY_NOT_FOUND(HttpStatus.NOT_FOUND, "CITY_NOT_FOUND", "해당 city 정보다 DB에 존재하지 않습니다"),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "LOCATION_NOT_FOUND", "해당 장소가 존재하지 않습니다"),
    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

}
