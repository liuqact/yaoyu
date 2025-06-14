# 用户模块设计文档

## 1. 系统架构设计

### 1.1 整体架构
```
用户模块
├── 认证服务 (Auth Service)
│   ├── 微信认证
│   ├── Token管理
│   └── 会话管理
├── 用户服务 (User Service)
│   ├── 用户管理
│   ├── 信息管理
│   └── 状态管理
├── 权限服务 (Permission Service)
│   ├── 角色管理
│   ├── 权限控制
│   └── 权限验证
└── 安全服务 (Security Service)
    ├── 安全控制
    ├── 日志审计
    └── 监控告警
```

### 1.2 技术架构
- 开发框架：Spring Boot
- 数据存储：MySQL + Redis
- 安全框架：Spring Security + JWT
- 缓存框架：Spring Cache
- 日志框架：Logback
- 监控框架：Spring Boot Actuator

## 2. 数据库设计

### 2.1 用户表 (t_user)
```sql
CREATE TABLE t_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    openid VARCHAR(64) NOT NULL COMMENT '微信openid',
    unionid VARCHAR(64) COMMENT '微信unionid',
    nickname VARCHAR(64) COMMENT '用户昵称',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    gender TINYINT COMMENT '性别：0-未知，1-男，2-女',
    country VARCHAR(64) COMMENT '国家',
    province VARCHAR(64) COMMENT '省份',
    city VARCHAR(64) COMMENT '城市',
    language VARCHAR(32) COMMENT '语言',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_openid (openid),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 2.2 用户扩展表 (t_user_extend)
```sql
CREATE TABLE t_user_extend (
    user_id BIGINT PRIMARY KEY COMMENT '用户ID',
    tags VARCHAR(255) COMMENT '用户标签',
    preferences TEXT COMMENT '用户偏好',
    settings TEXT COMMENT '用户设置',
    statistics TEXT COMMENT '用户统计',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES t_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户扩展表';
```

### 2.3 角色表 (t_role)
```sql
CREATE TABLE t_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    name VARCHAR(32) NOT NULL COMMENT '角色名称',
    code VARCHAR(32) NOT NULL COMMENT '角色编码',
    description VARCHAR(255) COMMENT '角色描述',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
```

### 2.4 用户角色关联表 (t_user_role)
```sql
CREATE TABLE t_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_role (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES t_user(id),
    FOREIGN KEY (role_id) REFERENCES t_role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';
```

### 2.5 权限表 (t_permission)
```sql
CREATE TABLE t_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    name VARCHAR(32) NOT NULL COMMENT '权限名称',
    code VARCHAR(32) NOT NULL COMMENT '权限编码',
    type TINYINT NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
    parent_id BIGINT COMMENT '父权限ID',
    path VARCHAR(255) COMMENT '权限路径',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';
```

### 2.6 角色权限关联表 (t_role_permission)
```sql
CREATE TABLE t_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES t_role(id),
    FOREIGN KEY (permission_id) REFERENCES t_permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';
```

### 2.7 操作日志表 (t_operation_log)
```sql
CREATE TABLE t_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    module VARCHAR(32) COMMENT '操作模块',
    operation VARCHAR(32) COMMENT '操作类型',
    method VARCHAR(255) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    result TEXT COMMENT '操作结果',
    ip VARCHAR(64) COMMENT 'IP地址',
    user_agent VARCHAR(255) COMMENT '用户代理',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_user_id (user_id),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
```

## 3. 接口设计

### 3.1 认证接口

#### 3.1.1 微信登录
```http
POST /api/v1/auth/wx-login
Content-Type: application/json

Request:
{
    "code": "string",          // 微信登录code
    "userInfo": {              // 用户信息
        "nickName": "string",  // 昵称
        "avatarUrl": "string", // 头像
        "gender": 0,           // 性别
        "country": "string",   // 国家
        "province": "string",  // 省份
        "city": "string"       // 城市
    }
}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "token": "string",     // 访问令牌
        "userInfo": {          // 用户信息
            "id": 0,           // 用户ID
            "nickname": "string", // 昵称
            "avatarUrl": "string", // 头像
            "gender": 0,       // 性别
            "country": "string", // 国家
            "province": "string", // 省份
            "city": "string"   // 城市
        }
    }
}
```

#### 3.1.2 刷新Token
```http
POST /api/v1/auth/refresh-token
Authorization: Bearer {token}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "token": "string"      // 新的访问令牌
    }
}
```

### 3.2 用户接口

#### 3.2.1 获取用户信息
```http
GET /api/v1/users/{userId}
Authorization: Bearer {token}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "id": 0,               // 用户ID
        "nickname": "string",  // 昵称
        "avatarUrl": "string", // 头像
        "gender": 0,           // 性别
        "country": "string",   // 国家
        "province": "string",  // 省份
        "city": "string",      // 城市
        "tags": ["string"],    // 标签
        "preferences": {},     // 偏好
        "settings": {},        // 设置
        "statistics": {}       // 统计
    }
}
```

#### 3.2.2 更新用户信息
```http
PUT /api/v1/users/{userId}
Authorization: Bearer {token}
Content-Type: application/json

Request:
{
    "nickname": "string",      // 昵称
    "avatarUrl": "string",     // 头像
    "gender": 0,               // 性别
    "country": "string",       // 国家
    "province": "string",      // 省份
    "city": "string",          // 城市
    "tags": ["string"],        // 标签
    "preferences": {},         // 偏好
    "settings": {}             // 设置
}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "id": 0,               // 用户ID
        "nickname": "string",  // 昵称
        "avatarUrl": "string", // 头像
        "gender": 0,           // 性别
        "country": "string",   // 国家
        "province": "string",  // 省份
        "city": "string",      // 城市
        "tags": ["string"],    // 标签
        "preferences": {},     // 偏好
        "settings": {}         // 设置
    }
}
```

### 3.3 权限接口

#### 3.3.1 获取用户权限
```http
GET /api/v1/permissions/user
Authorization: Bearer {token}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "roles": ["string"],   // 角色列表
        "permissions": ["string"] // 权限列表
    }
}
```

#### 3.3.2 验证权限
```http
POST /api/v1/permissions/verify
Authorization: Bearer {token}
Content-Type: application/json

Request:
{
    "permission": "string"     // 权限编码
}

Response:
{
    "code": 0,                 // 状态码
    "message": "string",       // 状态信息
    "data": {
        "hasPermission": true  // 是否有权限
    }
}
```

## 4. 缓存设计

### 4.1 用户信息缓存
- Key: `user:info:{userId}`
- Value: 用户信息JSON
- 过期时间：1小时

### 4.2 Token缓存
- Key: `token:{token}`
- Value: 用户ID
- 过期时间：2小时

### 4.3 权限缓存
- Key: `permission:user:{userId}`
- Value: 权限列表JSON
- 过期时间：1小时

## 5. 安全设计

### 5.1 认证安全
- 使用JWT进行身份认证
- Token包含用户ID、角色、权限信息
- Token有效期2小时
- 支持Token刷新机制

### 5.2 接口安全
- 所有接口需要Token认证
- 敏感接口需要角色权限验证
- 接口访问频率限制
- 接口参数验证和过滤

### 5.3 数据安全
- 敏感数据加密存储
- 传输数据加密
- 数据访问权限控制
- 数据操作审计日志

## 6. 部署设计

### 6.1 服务部署
- 使用Docker容器化部署
- 使用Nginx反向代理
- 使用Redis集群
- 使用MySQL主从复制

### 6.2 监控部署
- 使用Spring Boot Actuator
- 使用Prometheus监控
- 使用Grafana展示
- 使用ELK日志系统

## 7. 测试设计

### 7.1 单元测试
- 使用JUnit5
- 使用Mockito
- 测试覆盖率要求>80%
- 核心功能100%覆盖

### 7.2 接口测试
- 使用Postman
- 使用JMeter
- 测试所有接口
- 测试异常场景

### 7.3 性能测试
- 使用JMeter
- 测试并发能力
- 测试响应时间
- 测试系统稳定性 