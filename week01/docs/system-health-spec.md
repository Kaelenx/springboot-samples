# 系统健康检查接口规格文档

## 1. 核心目标
- 开发系统健康检查接口，返回项目基本运行状态信息，用于快速验证项目是否正常启动、服务是否可用。

## 2. 业务规则
- 接口路径：GET /api/health
- 无需请求参数
- 返回 JSON，包含 code、msg、data 三个字段
- code 固定为 200，表示成功
- msg 固定为 "success"
- data 为对象，包含 projectName、version、serverTime、status 四个字段：
  - projectName：当前项目名称
  - version：当前项目版本号
  - serverTime：当前服务器时间（ISO 8601 格式）
  - status：服务运行状态，固定为 "UP"

## 3. 技术约束
- 使用 Spring Boot 3.x
- 使用 Java 17
- 端口使用默认 8080
- 返回类型使用统一包装类 ResultVO<Map<String, Object>>

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
    "projectName": "springboot-samples",
    "version": "1.0.0",
    "serverTime": "2025-10-01T12:00:00",
    "status": "UP"
  }
}
```

## 5. 验收标准
- 项目能成功启动
- 访问 /api/health 返回 HTTP 状态码 200
- 返回 JSON 结构中包含 code、msg、data 三个字段
- data 字段包含指定的四个子字段，且各字段值为非空字符串，status 固定为 "UP"