# 代码规范文档

## 1. 命名规范
### 1.1 类名
- 使用大驼峰命名法（UpperCamelCase）
- 示例：`UserService`, `OrderController`

### 1.2 方法名
- 使用小驼峰命名法（lowerCamelCase）
- 示例：`getUserInfo`, `createOrder`

### 1.3 变量名
- 使用小驼峰命名法（lowerCamelCase）
- 示例：`userName`, `orderList`

### 1.4 常量名
- 使用全大写下划线分隔
- 示例：`MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE`

## 2. 代码格式规范
### 2.1 缩进
- 使用4个空格进行缩进
- 不使用Tab字符

### 2.2 行宽
- 每行代码不超过120个字符
- 超长行需要适当换行

### 2.3 空行
- 方法之间空一行
- 逻辑相关的代码块之间空一行
- 不相关的代码块之间空两行

## 3. 注释规范
### 3.1 类注释
```java
/**
 * 类的功能描述
 *
 * @author 作者名
 * @date 创建日期
 */
```

### 3.2 方法注释
```java
/**
 * 方法的功能描述
 *
 * @param param1 参数1的说明
 * @param param2 参数2的说明
 * @return 返回值的说明
 * @throws Exception 异常说明
 */
```

## 4. 异常处理规范
### 4.1 异常捕获
- 使用具体的异常类型
- 避免捕获所有异常（Exception）
- 记录详细的异常信息

### 4.2 异常抛出
- 使用自定义业务异常
- 提供清晰的错误信息
- 包含错误码

## 5. 日志规范
### 5.1 日志级别
- ERROR: 系统错误
- WARN: 警告信息
- INFO: 重要业务信息
- DEBUG: 调试信息

### 5.2 日志格式
```java
log.error("操作失败，原因：{}", errorMessage, exception);
log.info("用户{}登录成功", username);
```

## 6. 数据库规范
### 6.1 表名规范
- 使用小写字母
- 使用下划线分隔
- 示例：`user_info`, `order_detail`

### 6.2 字段名规范
- 使用小写字母
- 使用下划线分隔
- 示例：`user_name`, `create_time`

### 6.3 索引规范
- 主键使用 `id`
- 创建时间使用 `create_time`
- 更新时间使用 `update_time`
- 软删除使用 `is_deleted` 