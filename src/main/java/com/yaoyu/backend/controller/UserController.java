package com.yaoyu.backend.controller;

import com.yaoyu.backend.common.Result;
import com.yaoyu.backend.entity.User;
import com.yaoyu.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户Controller
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/{userId}")
    public Result<User> getUserInfo(@PathVariable Long userId) {
        return Result.success(userService.getById(userId));
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/{userId}")
    public Result<User> updateUserInfo(@PathVariable Long userId,
                                     @RequestParam(required = false) String nickname,
                                     @RequestParam(required = false) String avatarUrl,
                                     @RequestParam(required = false) Integer gender,
                                     @RequestParam(required = false) String country,
                                     @RequestParam(required = false) String province,
                                     @RequestParam(required = false) String city) {
        return Result.success(userService.updateUser(userId, nickname, avatarUrl, gender, country, province, city));
    }
} 