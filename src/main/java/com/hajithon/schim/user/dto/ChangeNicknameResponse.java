package com.hajithon.schim.user.dto;

import java.util.UUID;

public record ChangeNicknameResponse(
        UUID userId,
        String newNickname
) {
}
