package com.hajithon.schim.common.auth;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.user.User;
import com.hajithon.schim.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    public static final String LOGIN_USER_ID_ATTRIBUTE = "loginUserId";
    private static final String BEARER_PREFIX = "Bearer ";
    // 구현 예정
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        UUID token = parseToken(header.substring(BEARER_PREFIX.length()).trim());

        User user = userService.getByToken(token);

        request.setAttribute(LOGIN_USER_ID_ATTRIBUTE, user.getId());

        return true;
    }

    private UUID parseToken(String rawToken) {
        try {
            return UUID.fromString(rawToken);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }
}
