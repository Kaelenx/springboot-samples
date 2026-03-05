# 系统环境变量接口规格文档

## 1. 核心目标
- 开发系统环境变量查询接口，返回非敏感的系统级环境变量信息，用于验证服务器环境配置、排查环境依赖问题。

## 2. 业务规则
- 接口路径：GET /api/system/env
- 无需请求参数
- 返回 JSON，包含 code、msg、data 三个字段
- code 固定为 200，表示成功
- msg 固定为 "success"
- data 为对象，包含以下非敏感环境变量字段（存在则返回）：
  - JAVA_HOME：Java 安装目录
  - OS：服务器操作系统类型
- 过滤所有敏感环境变量（如密码、密钥、令牌等），仅返回通用非敏感信息

## 3. 技术约束
- 使用 Spring Boot 3.x
- 使用 Java 17
- 端口使用默认 8080
- 返回类型使用统一包装类 ResultVO<Map<String, String>>

## 4. 输入输出
### 4.1 输入
- 无请求体，无查询参数

### 4.2 输出
- 成功示例：
```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "JAVA_HOME": "/usr/lib/jvm/java-17-openjdk",
    "OS": "Linux"
  }
}
```

## 5. 验收标准
- 项目能成功启动
- 访问 /api/system/env 返回 HTTP 状态码 200
- 返回 JSON 结构中包含 code、msg、data 三个字段
- data 字段仅返回非敏感环境变量，无任何敏感信息泄露
- data 字段中的各子字段值为非空字符串（无对应环境变量时可缺省）