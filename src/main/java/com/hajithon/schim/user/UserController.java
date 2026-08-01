package com.hajithon.schim.user;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(@RequestBody UserCreateRequest request) {
        User user = userService.register(request.nickname());

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(UserCreateResponse.from(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMe(@LoginUser UUID userId) {
        User user = userService.getMe(userId);

        return ResponseEntity.ok(ApiResponse.of(UserInfoResponse.from(user)));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<ChangeNicknameResponse>> changeNickname(@LoginUser UUID userId, @RequestBody ChangeNicknameRequest request) {
        userService.changeNickname(userId, request.newNickname());

        return ResponseEntity.ok(ApiResponse.of(new ChangeNicknameResponse(userId, request.newNickname())));
    }
}


