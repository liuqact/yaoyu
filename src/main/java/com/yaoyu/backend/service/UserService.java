package com.yaoyu.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaoyu.backend.entity.User;

/**
 * 用户Service接口
 */
public interface UserService extends IService<User> {
    /**
     * 根据openid获取用户
     */
    User getByOpenid(String openid);

    /**
     * 创建用户
     */
    User createUser(String openid, String unionid, String nickname, String avatarUrl, Integer gender, String country, String province, String city, String language);

    /**
     * 更新用户信息
     */
    User updateUser(Long userId, String nickname, String avatarUrl, Integer gender, String country, String province, String city);
} 