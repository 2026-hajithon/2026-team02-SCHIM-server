package com.hajithon.schim.surf;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class SurfCursor {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public record Decoded(LocalDateTime createdAt, Long id) {}

    public static String encode(LocalDateTime createdAt, Long id) {
        String raw = createdAt.format(FORMATTER) + "|" + id;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(
                StandardCharsets.UTF_8
        ));
    }

    // cursor 페이지네이션 사용
    public static Decoded decode(String cursor) {
        try {
            String raw = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
            String[] parts = raw.split("\\|", 2);
            return new Decoded(LocalDateTime.parse(parts[0], FORMATTER), Long.parseLong(parts[1]));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }
    }
}
