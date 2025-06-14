# 数据库设计规范

## 1. 命名规范

### 1.1 数据库命名
- 使用小写字母
- 使用下划线分隔
- 使用项目名称作为前缀
- 示例：`yaoyu_user_db`, `yaoyu_order_db`

### 1.2 表命名
- 使用小写字母
- 使用下划线分隔
- 使用模块名作为前缀
- 使用名词复数形式
- 示例：`user_info`, `order_detail`, `product_category`

### 1.3 字段命名
- 使用小写字母
- 使用下划线分隔
- 避免使用数据库关键字
- 字段名应该具有描述性
- 示例：`user_name`, `create_time`, `update_time`

### 1.4 索引命名
- 主键索引：`pk_表名`
- 唯一索引：`uk_表名_字段名`
- 普通索引：`idx_表名_字段名`
- 外键索引：`fk_表名_字段名`
- 示例：`pk_user`, `uk_user_phone`, `idx_user_create_time`

## 2. 字段规范

### 2.1 必须字段
每个表必须包含以下字段：
```sql
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0:否,1:是)',
`create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
`update_by` bigint(20) DEFAULT NULL COMMENT '更新人'
```

### 2.2 字段类型规范
- 字符串类型：
  - 定长字符串使用 `char`
  - 变长字符串使用 `varchar`
  - 大文本使用 `text`
- 数值类型：
  - 整数使用 `int` 或 `bigint`
  - 小数使用 `decimal`
- 时间类型：
  - 日期时间使用 `datetime`
  - 时间戳使用 `timestamp`
- 布尔类型：
  - 使用 `tinyint(1)`

### 2.3 字段长度规范
- `varchar` 类型：
  - 手机号：`varchar(11)`
  - 用户名：`varchar(32)`
  - 密码：`varchar(64)`
  - URL：`varchar(255)`
- `int` 类型：
  - 状态字段：`tinyint(4)`
  - 数量字段：`int(11)`
  - ID字段：`bigint(20)`

### 2.4 字段默认值
- 字符串类型：`DEFAULT ''`
- 数值类型：`DEFAULT 0`
- 时间类型：`DEFAULT CURRENT_TIMESTAMP`
- 布尔类型：`DEFAULT 0`

## 3. 索引规范

### 3.1 索引设计原则
- 最左前缀原则
- 选择性高的字段优先
- 避免冗余索引
- 考虑查询性能
- 考虑写入性能

### 3.2 索引类型选择
- 主键索引：使用 `PRIMARY KEY`
- 唯一索引：使用 `UNIQUE KEY`
- 普通索引：使用 `KEY` 或 `INDEX`
- 联合索引：根据查询条件设计

### 3.3 索引数量控制
- 单表索引数量建议不超过5个
- 单个索引字段数建议不超过3个
- 避免重复索引

## 4. 表设计规范

### 4.1 表设计原则
- 遵循第三范式
- 适当冗余
- 考虑扩展性
- 考虑性能
- 考虑维护性

### 4.2 分表策略
- 按时间分表
- 按ID范围分表
- 按业务分表
- 分表字段选择

### 4.3 表关系设计
- 一对一关系
- 一对多关系
- 多对多关系
- 外键约束

## 5. SQL规范

### 5.1 查询规范
- 避免使用 `SELECT *`
- 使用具体的字段名
- 使用 `LIMIT` 限制结果集
- 使用 `WHERE` 条件过滤
- 使用 `ORDER BY` 排序

### 5.2 更新规范
- 使用 `WHERE` 条件
- 使用事务
- 批量更新
- 避免全表更新

### 5.3 删除规范
- 使用软删除
- 使用 `WHERE` 条件
- 使用事务
- 避免全表删除

## 6. 安全规范

### 6.1 权限控制
- 最小权限原则
- 定期审查权限
- 敏感操作审计
- 密码加密存储

### 6.2 数据安全
- 敏感数据加密
- 数据备份策略
- 数据恢复方案
- 数据脱敏处理

## 7. 性能规范

### 7.1 查询性能
- 使用索引
- 避免全表扫描
- 避免使用函数
- 避免使用 `OR`
- 避免使用 `LIKE '%xxx'`

### 7.2 写入性能
- 批量插入
- 使用事务
- 避免大事务
- 避免频繁更新

## 8. 维护规范

### 8.1 日常维护
- 定期优化表
- 定期清理数据
- 定期检查索引
- 定期备份数据

### 8.2 变更管理
- 变更需要评审
- 变更需要测试
- 变更需要回滚方案
- 变更需要记录

## 9. 文档规范

### 9.1 表文档
- 表名和说明
- 字段说明
- 索引说明
- 变更历史

### 9.2 变更文档
- 变更原因
- 变更内容
- 变更影响
- 变更时间

## 10. 示例

### 10.1 建表示例
```sql
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(1:正常,0:禁用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0:否,1:是)',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_phone` (`phone`),
  KEY `idx_user_status` (`status`),
  KEY `idx_user_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
```

### 10.2 索引示例
```sql
-- 主键索引
ALTER TABLE `user_info` ADD PRIMARY KEY (`id`);

-- 唯一索引
ALTER TABLE `user_info` ADD UNIQUE KEY `uk_user_phone` (`phone`);

-- 普通索引
ALTER TABLE `user_info` ADD KEY `idx_user_status` (`status`);

-- 联合索引
ALTER TABLE `user_info` ADD KEY `idx_user_status_create_time` (`status`, `create_time`);
``` 