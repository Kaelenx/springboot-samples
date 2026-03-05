# 系统应用信息接口规格文档

## 1. 核心目标
- 开发系统应用信息查询接口，返回当前项目运行的基础环境信息，用于排查环境配置问题、验证项目运行环境是否符合预期。

## 2. 业务规则
- 接口路径：GET /api/system/info
- 无需请求参数
- 返回 JSON，包含 code、msg、data 三个字段
- code 固定为 200，表示成功
- msg 固定为 "success"
- data 为对象，包含 javaVersion、springBootVersion、hostName、activeProfile 四个字段：
  - javaVersion：当前运行的 Java 版本号
  - springBootVersion：当前使用的 Spring Boot 版本号
  - hostName：服务器主机名
  - activeProfile：当前激活的 Spring 环境配置（多个以英文逗号分隔）

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
    "javaVersion": "17.0.11",
    "springBootVersion": "3.5.11",
    "hostName": "localhost",
    "activeProfile": "dev"
  }
}
```
## 5. 验收标准
- 项目能成功启动
- 访问 /api/system/info 返回 HTTP 状态码 200
- 返回 JSON 结构中包含 code、msg、data 三个字段
- data 字段包含指定的四个子字段，且各字段值为非空字符串（异常场景下允许 error 子字段替代）