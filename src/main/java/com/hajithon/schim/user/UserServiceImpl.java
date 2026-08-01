package com.hajithon.schim.user;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// 핵심 로직은 Impl에 구현
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private static final int NICKNAME_MAX_LENGTH = 15;

    private final UserRepository userRepository;

    @Override
    public User getByToken(UUID anonymousToken) {
        return userRepository.findByAnonymousToken(anonymousToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public User register(String nickname) {
        validateNickname(nickname);
        User user = User.create(nickname);

        return userRepository.save(user);
    }

    @Override
    public User getMe(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void changeNickname(UUID userId, String newNickname) {
        validateNickname(newNickname);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.changeNickname(newNickname);
        userRepository.save(user);
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_NICKNAME);
        }
    }
}
