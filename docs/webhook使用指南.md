# 企业微信 Webhook 使用指南

## 概述

Vita 框架提供了企业微信 Webhook 消息推送功能，支持文本、Markdown、图片、图文、文件等多种消息类型。

## 快速开始

### 1. 配置 Webhook

在 `application.yml` 中配置：

```yaml
vita:
  webhook:
    # 是否启用 webhook 功能
    enabled: true
    # 企业微信 webhook 地址
    url: https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxx-xxxx-xxxx-xxxx
```

**获取 Webhook 地址：**
1. 在企业微信群中添加"群机器人"
2. 复制 webhook 地址

### 2. 注入服务

```java
@Autowired
private WebhookService webhookService;
```

### 3. 发送消息

```java
try {
    webhookService.sendText("这是一条测试消息");
} catch (Exception e) {
    log.error("发送失败", e);
}
```

## 支持的消息类型

### 1. 文本消息

```java
// 简单文本
webhookService.sendText("这是一条测试消息");

// 带提及功能
List<String> userIds = List.of("zhangsan", "lisi");
List<String> mobiles = List.of("13800138000");
webhookService.sendTextWithMention(
    "请以下同事注意：@zhangsan @lisi", 
    userIds, 
    mobiles
);
```

### 2. Markdown 消息

```java
String markdown = "# 系统通知\n" +
                 "## 服务器状态\n" +
                 "- CPU 使用率：**85%**\n" +
                 "- 内存使用率：**72%**\n" +
                 "\n" +
                 "> 请注意服务器负载情况";

webhookService.sendMarkdown(markdown);
```

### 3. 图文消息

```java
// 单条图文
webhookService.sendSingleNews(
    "标题",
    "描述",
    "https://example.com/link",
    "https://example.com/image.jpg"
);

// 多条图文
List<WebhookMessage.NewsContent.Article> articles = new ArrayList<>();

WebhookMessage.NewsContent.Article article1 = new WebhookMessage.NewsContent.Article();
article1.setTitle("文章1")
        .setDescription("描述1")
        .setUrl("https://example.com/1")
        .setPicurl("https://example.com/img1.jpg");

articles.add(article1);
webhookService.sendNews(articles);
```

### 4. 图片消息

```java
// base64: 图片的 base64 编码
// md5: 图片的 MD5 值
webhookService.sendImage(base64, md5);
```

### 5. 文件消息

```java
// mediaId: 通过企业微信上传接口获取的文件 ID
webhookService.sendFile(mediaId);
```

## 实际应用场景

### 场景 1: 系统告警

```java
@Service
@Slf4j
public class AlertService {
    
    @Autowired
    private WebhookService webhookService;
    
    public void sendAlert(String serviceName, String errorMessage) {
        if (!webhookService.isConfigured()) {
            return;
        }
        
        try {
            String markdown = String.format(
                "# ⚠️ 系统告警\n" +
                "**服务**: %s\n" +
                "**错误**: %s\n" +
                "**时间**: %s\n" +
                "> 请立即处理",
                serviceName,
                errorMessage,
                LocalDateTime.now()
            );
            
            // @管理员
            List<String> admins = List.of("admin1", "admin2");
            webhookService.sendTextWithMention(
                markdown, 
                admins, 
                null
            );
        } catch (Exception e) {
            log.error("发送告警失败", e);
        }
    }
}
```

### 场景 2: 订单通知

```java
@Service
public class OrderNotificationService {
    
    @Autowired
    private WebhookService webhookService;
    
    public void notifyOrderCreated(Order order) {
        if (!webhookService.isConfigured()) {
            return;
        }
        
        try {
            webhookService.sendSingleNews(
                "新订单 #" + order.getOrderNo(),
                "金额: ¥" + order.getAmount(),
                "https://example.com/orders/" + order.getOrderNo(),
                null
            );
        } catch (Exception e) {
            log.error("发送订单通知失败", e);
            // 不抛出异常，避免影响订单创建流程
        }
    }
}
```

### 场景 3: 定时任务报告

```java
@Component
public class DailyReportTask {
    
    @Autowired
    private WebhookService webhookService;
    
    @Scheduled(cron = "0 0 9 * * ?") // 每天早上9点
    public void sendDailyReport() {
        if (!webhookService.isConfigured()) {
            return;
        }
        
        try {
            String markdown = "# 📊 每日报告\n" +
                             "**日期**: " + LocalDate.now() + "\n" +
                             "**新增用户**: 100\n" +
                             "**订单数量**: 50\n" +
                             "**收入**: ¥10,000\n";
            
            webhookService.sendMarkdown(markdown);
        } catch (Exception e) {
            log.error("发送日报失败", e);
        }
    }
}
```

## 异常处理

所有方法都会抛出异常，由调用方统一捕获：

```java
try {
    webhookService.sendText("消息内容");
    log.info("消息发送成功");
} catch (IllegalStateException e) {
    // Webhook 未配置
    log.error("Webhook 未配置", e);
} catch (Exception e) {
    // 其他异常（网络错误、HTTP 错误等）
    log.error("发送消息失败", e);
}
```

## 最佳实践

### ✅ 推荐做法

1. **始终使用 try-catch 捕获异常**
   ```java
   try {
       webhookService.sendText("消息");
   } catch (Exception e) {
       log.error("发送失败", e);
   }
   ```

2. **发送前检查配置**
   ```java
   if (!webhookService.isConfigured()) {
       log.warn("Webhook 未配置，跳过发送");
       return;
   }
   ```

3. **不要影响主业务流程**
   ```java
   try {
       webhookService.sendText("通知");
   } catch (Exception e) {
       log.error("发送通知失败", e);
       // 只记录日志，不抛出异常
   }
   ```

4. **记录日志便于排查**
   ```java
   try {
       webhookService.sendMarkdown(content);
       log.info("消息发送成功");
   } catch (Exception e) {
       log.error("消息发送失败, content={}", content, e);
   }
   ```

### ❌ 避免做法

1. **不要在循环中频繁发送**（有频率限制：每分钟最多 20 条）
2. **不要发送过长的内容**（文本最长 2048 字节，Markdown 最长 4096 字节）
3. **不要忘记处理异常**

## 注意事项

### 长度限制

| 消息类型 | 限制 |
|---------|------|
| 文本消息 | 最长 2048 字节 |
| Markdown | 最长 4096 字节 |
| 图文标题 | 最长 128 字节 |
| 图文描述 | 最长 512 字节 |
| 图片大小 | 不超过 2MB |
| 图文数量 | 最多 8 条 |
| 提及人数 | 最多 100 人 |

### 频率限制

- 每个机器人每分钟最多发送 20 条消息
- 建议实现重试机制和限流控制

### 确保消息必达

如果需要确保消息必达，建议使用消息队列异步发送：

```java
@Async
public void sendNotificationAsync(String content) {
    int retryCount = 3;
    for (int i = 0; i < retryCount; i++) {
        try {
            webhookService.sendText(content);
            return;
        } catch (Exception e) {
            log.error("第{}次发送失败", i + 1, e);
            if (i == retryCount - 1) {
                throw e;
            }
            Thread.sleep(1000 * (i + 1)); // 指数退避
        }
    }
}
```

## API 参考

### WebhookService

| 方法 | 说明 | 参数 |
|------|------|------|
| `sendText(content)` | 发送文本消息 | content: 文本内容 |
| `sendTextWithMention(content, users, mobiles)` | 发送带提及的文本 | content: 内容, users: userid列表, mobiles: 手机号列表 |
| `sendMarkdown(content)` | 发送 Markdown 消息 | content: Markdown 内容 |
| `sendSingleNews(title, desc, url, picurl)` | 发送单条图文 | title: 标题, desc: 描述, url: 链接, picurl: 图片URL |
| `sendNews(articles)` | 发送多条图文 | articles: 图文列表 |
| `sendImage(base64, md5)` | 发送图片 | base64: 图片编码, md5: 图片MD5 |
| `sendFile(mediaId)` | 发送文件 | mediaId: 文件ID |
| `sendMessage(message)` | 发送自定义消息 | message: 消息对象 |
| `isConfigured()` | 检查是否已配置 | 返回 boolean |

### WebhookMessage

静态工厂方法用于构建消息对象：

```java
WebhookMessage message = WebhookMessage.buildText("内容");
WebhookMessage message = WebhookMessage.buildMarkdown("# 标题");
WebhookMessage message = WebhookMessage.buildSingleNews("标题", "描述", "url", "picurl");
// ... 更多方法见 JavaDoc
```

## 常见问题

### Q1: 消息发送失败怎么办？

1. 检查 webhook URL 是否正确
2. 检查网络连接是否正常
3. 查看日志中的错误信息
4. 确认消息内容是否符合格式要求

### Q2: 如何调试？

可以先将 `enabled` 设置为 `false`，查看日志输出，确认配置正确后再启用。

### Q3: 会影响主业务吗？

不会，建议在 catch 中只记录日志，不抛出异常。如果需要确保通知发送成功，可以使用消息队列异步发送。

### Q4: 支持钉钉或其他平台吗？

当前仅支持企业微信。如需支持其他平台，可以扩展 `WebhookService` 或创建新的 Service。

## 更多信息

- 查看 `WebhookMessage.java` 和 `WebhookService.java` 的 JavaDoc 获取详细 API 文档
- 查看 `WebhookMessageTest.java` 了解单元测试示例
- 企业微信官方文档：https://work.weixin.qq.com/api/doc/90000/90136/91770
