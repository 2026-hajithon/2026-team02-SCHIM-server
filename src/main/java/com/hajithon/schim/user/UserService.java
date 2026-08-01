package com.hajithon.schim.user;

import java.util.UUID;

// DIP 구현. 외부에서는 UserService로 소통.
public interface UserService {
    User getByToken(UUID anonymousToken);
    User register(String nickname);
    User getMe(UUID userId);
    void changeNickname(UUID userId, String newNickname);
}
