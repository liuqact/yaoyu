# Git 规范文档

## 1. 分支管理规范

### 1.1 分支命名规范
- `main`: 主分支，用于生产环境
- `dev`: 开发分支，用于开发环境
- `feature/*`: 功能分支，用于开发新功能
- `hotfix/*`: 修复分支，用于修复生产环境的问题
- `release/*`: 发布分支，用于版本发布

### 1.2 分支保护规则
#### main分支
- 必须通过Pull Request才能合并
- 需要至少一个代码审查批准
- 禁止直接推送
- 必须通过CI/CD检查
- 合并后自动触发部署

#### dev分支
- 允许开发团队直接推送
- 建议进行代码审查
- 必须通过CI/CD检查
- 合并后自动触发测试环境部署

### 1.3 分支管理流程
#### 功能开发流程
1. 从`dev`分支创建`feature/功能名称`分支
2. 在功能分支上进行开发
3. 完成开发后，创建Pull Request到`dev`分支
4. 代码审查通过后合并到`dev`分支
5. 功能测试通过后，通过`dev`分支合并到`main`分支

#### 问题修复流程
1. 从`main`分支创建`hotfix/问题描述`分支
2. 在修复分支上进行修复
3. 完成修复后，创建Pull Request到`main`分支
4. 代码审查通过后合并到`main`分支
5. 将修复同步到`dev`分支

#### 版本发布流程
1. 从`dev`分支创建`release/版本号`分支
2. 在发布分支上进行版本测试和bug修复
3. 测试通过后，创建Pull Request到`main`分支
4. 代码审查通过后合并到`main`分支
5. 将发布分支的修改同步到`dev`分支

## 2. 提交信息规范

### 2.1 提交信息格式
```
<type>(<scope>): <subject>

<body>

<footer>
```

### 2.2 提交类型
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式（不影响代码运行的变动）
- `refactor`: 重构（既不是新增功能，也不是修改bug的代码变动）
- `perf`: 性能优化
- `test`: 增加测试
- `chore`: 构建过程或辅助工具的变动

### 2.3 提交范围
- 影响范围，如：user, auth, order等

### 2.4 提交描述
- 简短描述，不超过50个字符
- 以动词开头，使用第一人称现在时
- 第一个字母小写
- 结尾不加句号

### 2.5 提交示例
```
feat(user): 添加用户注册功能

- 实现手机号注册
- 实现邮箱注册
- 添加验证码功能

Closes #123
```

## 3. 代码审查规范

### 3.1 审查内容
- 代码是否符合项目规范
- 代码是否有潜在问题
- 代码是否易于维护
- 代码是否有重复
- 代码是否有测试覆盖

### 3.2 审查流程
1. 开发者创建Pull Request
2. 至少一名审查者进行代码审查
3. 审查者提出修改建议
4. 开发者根据建议修改代码
5. 审查者批准合并

### 3.3 审查标准
- 代码风格符合规范
- 代码逻辑清晰
- 代码注释完整
- 测试覆盖充分
- 没有安全隐患

## 4. 版本管理规范

### 4.1 版本号规范
采用语义化版本号：`主版本号.次版本号.修订号`
- 主版本号：不兼容的API修改
- 次版本号：向下兼容的功能性新增
- 修订号：向下兼容的问题修正

### 4.2 版本发布流程
1. 确定版本号
2. 创建发布分支
3. 更新版本号
4. 更新CHANGELOG
5. 创建发布标签
6. 合并到主分支
7. 部署到生产环境

## 5. 工作流程规范

### 5.1 开发流程
1. 从 `dev` 分支创建功能分支
2. 在功能分支上开发
3. 提交代码到功能分支
4. 创建合并请求
5. 代码审查
6. 合并到 `dev` 分支

### 5.2 发布流程
1. 从 `dev` 分支创建发布分支
2. 在发布分支上测试
3. 修复问题
4. 更新版本号
5. 合并到 `main` 分支
6. 打标签

### 5.3 修复流程
1. 从 `main` 分支创建修复分支
2. 在修复分支上修复问题
3. 提交代码到修复分支
4. 创建合并请求
5. 代码审查
6. 合并到 `main` 和 `dev` 分支

## 6. 工具使用规范

### 6.1 Git 配置
```bash
# 配置用户信息
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# 配置换行符
git config --global core.autocrlf input

# 配置编辑器
git config --global core.editor vim
```

### 6.2 常用命令
```bash
# 创建分支
git checkout -b feature/new-feature

# 提交代码
git add .
git commit -m "feat: 添加新功能"

# 推送代码
git push origin feature/new-feature

# 拉取代码
git pull origin dev

# 合并分支
git merge feature/new-feature
```

### 6.3 标签管理
```bash
# 创建标签
git tag -a v1.0.0 -m "版本1.0.0"

# 推送标签
git push origin v1.0.0

# 删除标签
git tag -d v1.0.0
git push origin :refs/tags/v1.0.0
```

### 6.4 分支操作
```bash
# 查看所有分支
git branch -a

# 删除本地分支
git branch -d feature/old-feature

# 删除远程分支
git push origin --delete feature/old-feature

# 强制更新本地分支
git fetch --all
git reset --hard origin/dev
``` 