package com.yaoyu.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 吆鱼小程序后端服务启动类
 */
@SpringBootApplication
@EnableTransactionManagement
public class YaoyuBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(YaoyuBackendApplication.class, args);
    }
} 