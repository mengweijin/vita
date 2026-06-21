package com.github.mengweijin.vita.framework.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Webhook 使用示例（仅用于测试和演示）
 * <p>
 * 这个类展示了如何在实际业务中使用 WebhookService
 * 注意：这个类只存在于 test 包中，不会打包到生产环境
 * </p>
 *
 * @author mengweijin
 * @since 2026-06-20
 */
@Slf4j
@Component
public class WebhookUsageExample {

    @Autowired
    private WebhookService webhookService;

    /**
     * 示例 1: 发送简单文本消息
     */
    public void example1_sendText() {
        try {
            webhookService.sendText("这是一条测试消息");
            log.info("文本消息发送成功");
        } catch (Exception e) {
            log.error("发送文本消息失败", e);
        }
    }

    /**
     * 示例 2: 发送带提及的消息
     */
    public void example2_sendTextWithMention() {
        try {
            List<String> userIds = List.of("zhangsan", "lisi");
            List<String> mobiles = List.of("13800138000");

            webhookService.sendTextWithMention(
                    "请以下同事注意：@zhangsan @lisi",
                    userIds,
                    mobiles
            );
            log.info("带提及的文本消息发送成功");
        } catch (Exception e) {
            log.error("发送带提及的文本消息失败", e);
        }
    }

    /**
     * 示例 3: 发送 Markdown 格式的系统告警
     */
    public void example3_sendSystemAlert() {
        try {
            String markdown = "# ⚠️ 系统告警\n" +
                    "## 服务器状态异常\n" +
                    "- **服务**: user-service\n" +
                    "- **错误**: 数据库连接超时\n" +
                    "- **时间**: " + java.time.LocalDateTime.now() + "\n" +
                    "\n" +
                    "> 请立即处理";

            webhookService.sendMarkdown(markdown);
            log.info("系统告警发送成功");
        } catch (Exception e) {
            log.error("发送系统告警失败", e);
        }
    }

    /**
     * 示例 4: 发送订单通知（图文消息）
     */
    public void example4_sendOrderNotification(String orderNo, Double amount) {
        try {
            webhookService.sendSingleNews(
                    "新订单 #" + orderNo,
                    "金额: ¥" + String.format("%.2f", amount),
                    "https://example.com/orders/" + orderNo,
                    null
            );
            log.info("订单通知发送成功");
        } catch (Exception e) {
            log.error("发送订单通知失败", e);
        }
    }

    /**
     * 示例 5: 发送多条图文消息
     */
    public void example5_sendMultipleNews() {
        try {
            WebhookMessage.NewsContent.Article article1 = new WebhookMessage.NewsContent.Article();
            article1.setTitle("系统升级通知")
                    .setDescription("系统将于今晚进行升级维护")
                    .setUrl("https://example.com/notice/1")
                    .setPicurl("https://example.com/image/notice.jpg");

            WebhookMessage.NewsContent.Article article2 = new WebhookMessage.NewsContent.Article();
            article2.setTitle("新功能上线")
                    .setDescription("新增文件存储功能")
                    .setUrl("https://example.com/notice/2")
                    .setPicurl("https://example.com/image/feature.jpg");

            webhookService.sendNews(List.of(article1, article2));
            log.info("图文消息发送成功");
        } catch (Exception e) {
            log.error("发送图文消息失败", e);
        }
    }

    /**
     * 示例 6: 检查配置后再发送
     */
    public void example6_checkBeforeSend() {
        try {
            webhookService.sendText("这是一条测试消息");
            log.info("消息发送成功");
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }

    /**
     * 示例 7: 在实际业务中使用（如 Service 层）
     */
    public void example7_businessUsage(String serviceName, String errorMessage) {
        try {
            String markdown = String.format(
                    "# 系统异常\n**服务**: %s\n**错误**: %s",
                    serviceName,
                    errorMessage
            );

            // @管理员
            List<String> admins = List.of("admin1", "admin2");
            webhookService.sendTextWithMention(markdown, admins, null);

            log.info("系统异常通知发送成功");
        } catch (Exception e) {
            log.error("发送系统异常通知失败", e);
            // 不抛出异常，避免影响主业务流程
        }
    }

    /**
     * 示例 8: 批量发送消息
     */
    public void example8_batchSend() {
        try {
            // 发送多条消息
            webhookService.sendText("第一条消息");
            webhookService.sendMarkdown("# 第二条消息");
            webhookService.sendSingleNews("第三条", "描述", "https://example.com", null);

            log.info("批量消息发送成功");
        } catch (Exception e) {
            log.error("批量发送消息失败，已发送的消息可能已成功", e);
            // 可以根据业务需求决定是否需要回滚或补偿
        }
    }

    /**
     * 示例 9: 构建自定义消息并发送
     */
    public void example9_customMessage() {
        try {
            // 直接构建消息对象
            WebhookMessage message = WebhookMessage.buildMarkdown(
                    "# 自定义消息\n这是一条自定义的 Markdown 消息"
            );

            // 可以进一步自定义
            // message.setMsgtype("markdown");
            // ... 其他自定义逻辑

            webhookService.sendMessage(message);
            log.info("自定义消息发送成功");
        } catch (Exception e) {
            log.error("发送自定义消息失败", e);
        }
    }
}
