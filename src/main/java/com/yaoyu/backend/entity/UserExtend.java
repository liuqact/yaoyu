package com.yaoyu.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户扩展实体
 */
@Data
@TableName("t_user_extend")
public class UserExtend {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户标签
     */
    private String tags;

    /**
     * 用户偏好
     */
    private String preferences;

    /**
     * 用户设置
     */
    private String settings;

    /**
     * 用户统计
     */
    private String statistics;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 