# API 规范文档

## 1. URL 规范
- 基础格式：`/api/v1/{模块名}/{资源名}`
- 示例：
  - 用户模块：`/api/v1/users`
  - 订单模块：`/api/v1/orders`
  - 商品模块：`/api/v1/products`

## 2. HTTP 方法使用规范
- GET    - 查询资源
- POST   - 创建资源
- PUT    - 更新资源（全量更新）
- PATCH  - 更新资源（部分更新）
- DELETE - 删除资源

## 3. 请求参数规范
### 3.1 查询参数
- 分页参数：`page=1&size=20&sort=createTime,desc`
- 时间格式：`yyyy-MM-dd HH:mm:ss`

### 3.2 请求体格式
- Content-Type: application/json
- 请求体示例：
```json
{
    "name": "张三",
    "age": 18
}
```

## 4. 响应格式规范
### 4.1 统一响应格式
```json
{
    "code": 200,           // 状态码
    "message": "success",  // 提示信息
    "data": {},           // 响应数据
    "timestamp": 1679308800000,  // 时间戳
    "requestId": "req-123456"    // 请求ID
}
```

### 4.2 分页响应格式
```json
{
    "content": [],         // 数据列表
    "totalPages": 10,     // 总页数
    "totalElements": 100, // 总记录数
    "currentPage": 1,     // 当前页码
    "pageSize": 20        // 每页大小
}
```

## 5. 状态码规范
- 200: 成功
- 400: 请求参数错误
- 401: 未授权
- 403: 禁止访问
- 404: 资源不存在
- 500: 服务器内部错误

## 6. 接口文档规范
- 使用 Swagger 注解进行接口文档说明
- 必须包含接口描述、参数说明、返回值说明
- 示例：
```java
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public ApiResponse<UserVO> getUser(@PathVariable Long id) {
        // 实现逻辑
    }
}
```

## 7. 接口安全规范
### 7.1 请求头规范
- Authorization: Bearer {token}
- Content-Type: application/json
- X-Request-Id: {requestId}

### 7.2 接口限流
- 在响应头中返回限流信息：
  - X-RateLimit-Limit: 限制次数
  - X-RateLimit-Remaining: 剩余次数
  - X-RateLimit-Reset: 重置时间 