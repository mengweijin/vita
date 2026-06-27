package com.github.mengweijin.vita.framework.webhook;

import com.github.mengweijin.vita.framework.properties.VitaProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

/**
 * 企业微信 Webhook 服务
 * 支持发送各种类型的消息，异常直接抛出由调用方统一捕获
 *
 * <p><b>配置示例：</b></p>
 * <pre>{@code
 * vita:
 *   webhook:
 *     enabled: true
 *     url: https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxx-xxxx-xxxx-xxxx
 * }</pre>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * @Autowired
 * private WebhookService webhookService;
 *
 * // 发送文本消息
 * try {
 *     webhookService.sendText("这是一条测试消息");
 * } catch (Exception e) {
 *     log.error("发送失败", e);
 * }
 *
 * // 发送 Markdown 消息
 * try {
 *     String markdown = "# 系统通知\n服务器状态正常";
 *     webhookService.sendMarkdown(markdown);
 * } catch (Exception e) {
 *     log.error("发送失败", e);
 * }
 *
 * // 发送带提及的消息
 * try {
 *     List<String> userIds = List.of("zhangsan", "lisi");
 *     webhookService.sendTextWithMention("@zhangsan @lisi 请注意", userIds, null);
 * } catch (Exception e) {
 *     log.error("发送失败", e);
 * }
 * }</pre>
 *
 * @author mengweijin
 * @see WebhookMessage
 * @since 2026-06-20
 */
@Slf4j
@Service
@AllArgsConstructor
public class WebhookService {

    private final VitaProperties vitaProperties;

    private final RestTemplate restTemplate;

    /**
     * 发送文本消息
     *
     * @param content 文本内容
     */
    @Async
    public void sendText(String content) {
        sendMessage(() -> WebhookMessage.buildText(content));
    }

    /**
     * 发送文本消息（带提及）
     *
     * @param content          文本内容
     * @param mentionedList    被提及人的 userid 列表
     * @param mentionedMobiles 被提及人的手机号列表
     */
    @Async
    public void sendTextWithMention(String content, List<String> mentionedList, List<String> mentionedMobiles) {
        sendMessage(() -> WebhookMessage.buildTextWithMention(content, mentionedList, mentionedMobiles));
    }

    /**
     * 发送 Markdown 消息
     *
     * @param content Markdown 内容
     */
    @Async
    public void sendMarkdown(String content) {
        sendMessage(() -> WebhookMessage.buildMarkdown(content));
    }

    /**
     * 发送图片消息
     *
     * @param base64 图片 base64 编码
     * @param md5    图片 MD5
     */
    @Async
    public void sendImage(String base64, String md5) {
        sendMessage(() -> WebhookMessage.buildImage(base64, md5));
    }

    /**
     * 发送图文消息
     *
     * @param articles 图文列表
     */
    @Async
    public void sendNews(List<WebhookMessage.NewsContent.Article> articles) {
        sendMessage(() -> WebhookMessage.buildNews(articles));
    }

    /**
     * 发送单条图文消息
     *
     * @param title       标题
     * @param description 描述
     * @param url         链接
     * @param picurl      图片链接
     */
    @Async
    public void sendSingleNews(String title, String description, String url, String picurl) {
        sendMessage(() -> WebhookMessage.buildSingleNews(title, description, url, picurl));
    }

    /**
     * 发送文件消息
     *
     * @param mediaId 文件 ID
     */
    @Async
    public void sendFile(String mediaId) {
        sendMessage(() -> WebhookMessage.buildFile(mediaId));
    }

    /**
     * 发送自定义消息
     *
     * @param messageSupplier 消息对象
     */
    private void sendMessage(Supplier<WebhookMessage> messageSupplier) {
        if (!vitaProperties.getWebhook().getEnabled()) {
            log.debug("Webhook is disabled");
            return;
        }

        String webhookUrl = vitaProperties.getWebhook().getUrl();
        if (!StringUtils.hasText(webhookUrl)) {
            throw new IllegalStateException("Webhook URL is not configured");
        }

        try {
            WebhookMessage message = messageSupplier.get();
            String json = message.toJson();
            log.debug("Sending webhook message: {}", json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            String response = restTemplate.postForObject(webhookUrl, entity, String.class);
            log.info("Webhook message sent successfully: {}", response);
        } catch (Exception e) {
            log.error("Error sending webhook message", e);
        }
    }

}
