package com.yaoyu.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoyu.backend.entity.User;
import com.yaoyu.backend.mapper.UserMapper;
import com.yaoyu.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByOpenid(String openid) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(String openid, String unionid, String nickname, String avatarUrl, Integer gender, String country, String province, String city, String language) {
        User user = new User();
        user.setOpenid(openid);
        user.setUnionid(unionid);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        user.setGender(gender);
        user.setCountry(country);
        user.setProvince(province);
        user.setCity(city);
        user.setLanguage(language);
        user.setStatus(1);
        save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(Long userId, String nickname, String avatarUrl, Integer gender, String country, String province, String city) {
        User user = getById(userId);
        if (user != null) {
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            user.setGender(gender);
            user.setCountry(country);
            user.setProvince(province);
            user.setCity(city);
            updateById(user);
        }
        return user;
    }
} 