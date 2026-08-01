package com.hajithon.schim.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 규칙을 위반했습니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "정의되지 않은 카테고리입니다."),
    INVALID_REACTION(HttpStatus.BAD_REQUEST, "정의되지 않은 반응입니다."),
    INVALID_CURSOR(HttpStatus.BAD_REQUEST, "커서 디코딩에 실패했습니다."),
    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "파일 형식이 PNG가 아닙니다."),
    IMAGE_TOO_LARGE(HttpStatus.BAD_REQUEST, "파일 크기가 10MB를 초과했습니다."),
    IMAGE_DIMENSION_EXCEEDED(HttpStatus.BAD_REQUEST, "파일의 크기가 2160x2880를 초과했습니다."),
    INVALID_HIDDEN_CATEGORIES(HttpStatus.BAD_REQUEST, "최소 1개의 카테고리를 선택해주세요."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 없거나 형식에 맞지 않습니다."),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰에 해당하는 사용자가 없습니다."),
    CANNOT_OPEN_OWN_GUESTBOOK(HttpStatus.BAD_REQUEST, "자신의 방명록을 열 수 없습니다."),
    GUESTBOOK_FORBIDDEN(HttpStatus.FORBIDDEN, "다른 사람의 방명록을 수정 및 삭제할 수 없습니다."),

    GUESTBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "방명록을 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "콘텐츠를 찾을 수 없습니다."),

    EXTERNAL_SEARCH_FAILED(HttpStatus.BAD_GATEWAY, "외부 검색 API에 장애가 발생했습니다.");
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
