# Spring Boot 第十周作业 —— 实现思路与过程

## 一、项目概述

### 1.1 需求分析

本次作业要求使用 Spring Boot + `@Scheduled` 实现两个定时任务功能：

1. **定点发送邮件** — 每年特定日期自动发送生日祝福卡片邮件
2. **定点更新数据库信息** — 每 5 分钟从和风天气 API 拉取实时天气数据，存入 MySQL 数据库，并发送天气报告邮件

### 1.2 技术栈

| 技术 | 版本 | 用途 |
|---|---|---|
| Spring Boot | 3.5.14 | 基础框架 |
| Java | 17 | 开发语言 |
| spring-boot-starter-web | 内置 | Web 服务、RestTemplate |
| spring-boot-starter-mail | 内置 | 邮件发送（JavaMailSender） |
| spring-boot-starter-data-jpa | 内置 | ORM 框架，操作 MySQL |
| mysql-connector-j | 内置 | MySQL 驱动 |
| Lombok | 内置 | 简化代码（@Slf4j、@Data 等） |
| Jackson | 内置 | JSON 解析（ObjectMapper） |

---

## 二、项目结构

```
src/main/java/com/cookie/springbootstudyweek10/
├── SpringbootStudyWeek10Application.java    # 启动类（@EnableScheduling）
├── controller/
│   └── MailController.java                  # 邮件 API 接口（已有）
├── entity/
│   └── WeatherData.java                     # [新增] 天气数据实体类
├── repository/
│   └── WeatherDataRepository.java           # [新增] JPA Repository
├── service/
│   ├── MailService.java                     # 邮件服务（已有）
│   ├── StockService.java                    # 股票服务（已有）
│   └── WeatherService.java                  # [新增] 天气 API 服务
└── task/
    ├── SpringBootTimerTask.java             # Timer 定时器（已有）
    ├── StockScheduleTask.java               # 股票定时任务（已有）
    ├── BirthdayScheduleTask.java            # [新增] 生日祝福定时任务
    └── WeatherScheduleTask.java             # [新增] 天气数据定时任务
```

---

## 三、功能一：定时发送生日祝福邮件

### 3.1 实现思路

生日祝福邮件功能的核心逻辑是：**在指定日期触发一个 `@Scheduled` 定时任务，构建一封 HTML 富文本祝福邮件，通过已有的 `MailService` 发送出去。**

由于该功能不涉及外部 API 调用，仅需组装邮件内容并发送，因此无需额外创建 Service 层，直接在 Task 层完成所有逻辑，保持代码简洁。

### 3.2 关键实现

#### （1）配置注入

在 `application.yml` 中定义生日相关配置，通过 `@Value` 注解注入到任务类中：

```yaml
custom:
  birthday-email: 16422802@qq.com   # 收件人邮箱
  birthday-name: "mqx"              # 收件人称呼
  birthday-month: 5                 # 生日月份
  birthday-day: 12                  # 生日日期
```

#### （2）Cron 表达式设计

Spring 的 `@Scheduled` 使用 6 位 Cron 表达式：`秒 分 时 日 月 周`

```
@Scheduled(cron = "0 09 20 13 5 ?")
```

- `0` — 第 0 秒
- `09` — 第 09 分钟
- `20` — 20 点
- `13` — 13 日
- `5` — 5 月
- `?` — 不指定星期几（当指定了日期时，星期字段必须用 `?`）

即：每年 5 月 13 日 20:09 触发。

#### （3）HTML 邮件构建

采用与现有 `StockScheduleTask.buildStockHtmlContent()` 相同的模式，通过字符串拼接构建 HTML：

- 彩色渐变头部（`linear-gradient(135deg, #ff6b6b, #feca57)`）
- 个性化问候语（使用注入的 `birthdayName`）
- 生日祝福正文
- 日期显示和页脚

#### （4）邮件发送

复用已有的 `MailService.sendHtmlMail()` 方法，该方法内部使用 `MimeMessageHelper` 处理 HTML 格式邮件。

### 3.3 完整流程

```
@Scheduled 触发 → 构建 HTML 祝福内容 → MailService.sendHtmlMail() → 邮件发出
```

---

## 四、功能二：定时拉取天气数据并更新数据库

### 4.1 实现思路

天气功能比生日邮件复杂，涉及 **外部 API 调用 + 数据库存储 + 邮件发送** 三个环节。参照已有的 `StockService` + `StockScheduleTask` 分层模式：

- **WeatherService**（Service 层）：负责调用和风天气 API、解析返回数据
- **WeatherScheduleTask**（Task 层）：负责定时调度、调用 Service 获取数据、存入数据库、发送邮件

数据库部分使用 Spring Data JPA + MySQL，新增 `WeatherData` 实体和 `WeatherDataRepository`。

### 4.2 和风天气 API 对接

#### （1）API 请求格式

根据和风天气官方文档，完整请求由以下部分组成：

```
https://{API-Host}/v7/weather/now?location={LocationID}
```

- **API Host**：开发者专属域名（如 `kg78m32vp4.re.qweatherapi.com`）
- **LocationID**：城市标识（如 `101190101`）
- **身份认证**：通过请求头传递 API Key

#### （2）身份认证方式

和风天气支持两种认证方式，本项目采用 **API KEY** 方式：

```java
HttpHeaders headers = new HttpHeaders();
headers.set("X-QW-Api-Key", apiKey);       // API KEY 认证
headers.set("Accept-Encoding", "gzip, deflate");  // 声明支持 Gzip
```

#### （3）Gzip 解压处理

和风天气 API 返回的数据经过 Gzip 压缩，`RestTemplate` 默认不会自动解压。因此：

1. 将响应类型设为 `byte[]` 获取原始字节
2. 使用 `GZIPInputStream` 手动解压
3. 将解压后的字节转为 UTF-8 字符串
4. 再交给 Jackson `ObjectMapper` 解析 JSON

```java
// 获取字节数组
ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
byte[] bodyBytes = response.getBody();

// Gzip 解压
try (GZIPInputStream gzipIn = new GZIPInputStream(new ByteArrayInputStream(bodyBytes));
     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
    byte[] buffer = new byte[1024];
    int len;
    while ((len = gzipIn.read(buffer)) != -1) {
        out.write(buffer, 0, len);
    }
    rawData = out.toString(StandardCharsets.UTF_8);
}
```

#### （4）JSON 解析

和风天气返回标准 JSON 格式，使用 Jackson `ObjectMapper` 解析（不同于股票 API 的自定义分隔格式）：

```json
{
  "code": "200",
  "now": {
    "temp": "24",
    "text": "多云",
    "feelsLike": "26",
    "humidity": "72",
    "windDir": "东南风",
    "windSpeed": "3",
    "pressure": "1003",
    "vis": "16",
    "obsTime": "2020-06-30T21:40+08:00"
  }
}
```

解析步骤：
1. 校验 `code` 是否为 `"200"`（API 调用成功）
2. 提取 `now` 节点中的各项天气数据
3. 封装到 `Map<String, String>` 返回

### 4.3 数据库存储设计

#### （1）实体类 WeatherData

使用 JPA `@Entity` 注解映射到 MySQL 的 `weather_data` 表：

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long（自增主键） | 记录 ID |
| temp | String | 温度 |
| text | String | 天气状况描述 |
| feelsLike | String | 体感温度 |
| humidity | String | 相对湿度 |
| windDir | String | 风向 |
| windSpeed | String | 风速 |
| pressure | String | 大气压强 |
| vis | String | 能见度 |
| obsTime | String | 观测时间 |
| createTime | LocalDateTime | 入库时间（@PrePersist 自动生成） |

#### （2）Repository 层

继承 `JpaRepository<WeatherData, Long>`，自动获得 CRUD 方法，无需手写 SQL。

#### （3）JPA 配置

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update    # 自动建表/更新表结构
    show-sql: true         # 控制台打印 SQL
```

`ddl-auto: update` 会在启动时自动根据实体类创建或更新表结构，适合开发阶段使用。

### 4.4 定时任务流程

```
@Scheduled(fixedRate = 300000) 每5分钟触发
    │
    ├── 1. 调用 WeatherService.getRealTimeWeather() 获取天气数据
    │
    ├── 2. 日志输出结构化天气信息
    │
    ├── 3. 构建 WeatherData 实体，保存到 MySQL
    │
    ├── 4. 构建天气报告 HTML 邮件
    │
    └── 5. MailService.sendHtmlMail() 发送邮件
```

`fixedRate = 300000` 表示从上一次任务**开始**后 300000ms（5 分钟）再次执行，保证固定间隔。

### 4.5 天气报告邮件设计

参照 `StockScheduleTask` 的股票报告邮件风格，构建蓝色主题的天气卡片：

- 头部：蓝色背景 `#3498db`，标题 "Spring Boot 定时天气报告"
- 核心数据：大号字体显示当前温度和天气状况
- 详细表格：体感温度、湿度、风向风速、气压、能见度、观测时间
- 页脚：自动发送声明

---

## 五、关键设计决策

| 决策 | 原因 |
|---|---|
| 生日功能不需要单独 Service | 仅涉及邮件组装和发送，无外部 API 调用，保持简洁 |
| 天气用 ObjectMapper 而非手动解析 | QWeather 返回标准 JSON，Jackson 是最佳选择；股票 API 用手动 split 是因为其返回自定义分隔格式 |
| 天气用 fixedRate 而非 fixedDelay | fixedRate 保证固定 5 分钟间隔；fixedDelay 会在任务结束后再等 5 分钟，导致时间漂移 |
| API Key 通过请求头传递 | 和风天气官方推荐 `X-QW-Api-Key` 请求头方式，比 URL 参数更安全 |
| 手动 Gzip 解压 | RestTemplate 默认不自动解压 Gzip 响应，需要手动处理 |
| 使用 MySQL 而非 H2 | 持久化存储，数据不会因应用重启丢失 |

---

## 六、运行与验证

### 6.1 前置准备

1. 本地 MySQL 创建数据库：
   ```sql
   CREATE DATABASE weather_db;
   ```
2. 确认 `application.yml` 中 MySQL 连接信息正确（host、port、username、password）
3. 确认和风天气 API Key 和 LocationID 已配置

### 6.2 验证步骤

| 测试项 | 方式 | 预期结果 |
|---|---|---|
| 天气 API 调用 | 运行 `WeatherScheduleTaskTest.testGetRealTimeWeather()` | 控制台输出天气数据 Map |
| 天气定时任务完整流程 | 运行 `WeatherScheduleTaskTest.testFetchWeatherData()` | 数据写入 MySQL + 邮箱收到天气报告 |
| 生日邮件发送 | 运行 `BirthdayScheduleTaskTest.testSendBirthdayGreeting()` | 邮箱收到生日祝福邮件 |
| 定时任务自动触发 | 启动 Spring Boot 应用，观察控制台日志 | 天气任务每 5 分钟执行一次，数据持续入库 |
| 数据库数据验证 | 连接 MySQL 查询 `SELECT * FROM weather_data;` | 可见天气数据记录，每 5 分钟新增一条 |

---

## 七、新增/修改文件清单

| 文件 | 操作 | 说明 |
|---|---|---|
| `pom.xml` | 修改 | 添加 spring-boot-starter-data-jpa 和 mysql-connector-j 依赖 |
| `application.yml` | 修改 | 添加 MySQL 数据源、JPA 配置、生日和天气自定义配置 |
| `entity/WeatherData.java` | 新增 | JPA 实体类，映射 weather_data 表 |
| `repository/WeatherDataRepository.java` | 新增 | Spring Data JPA 接口 |
| `service/WeatherService.java` | 新增 | 和风天气 API 调用与数据解析 |
| `task/BirthdayScheduleTask.java` | 新增 | 生日祝福邮件定时任务 |
| `task/WeatherScheduleTask.java` | 新增 | 天气数据定时拉取、入库、发邮件 |
| `test/.../BirthdayScheduleTaskTest.java` | 新增 | 生日任务测试 |
| `test/.../WeatherScheduleTaskTest.java` | 新增 | 天气任务测试 |
