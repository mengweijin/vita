package com.github.mengweijin.vita.framework.webhook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Webhook 消息构建测试
 * 测试各种消息类型的 JSON 序列化是否正确
 *
 * <p>这些测试也可以作为使用示例参考</p>
 *
 * @author mengweijin
 * @since 2026-06-20
 */
@SpringBootTest
class WebhookMessageTest {

    /**
     * 测试文本消息 JSON 生成
     */
    @Test
    void testBuildText() {
        WebhookMessage message = WebhookMessage.buildText("这是一条测试消息");
        String json = message.toJson();
        System.out.println("文本消息 JSON: " + json);

        // 验证 JSON 格式
        assert json.contains("\"msgtype\":\"text\"");
        assert json.contains("\"content\":\"这是一条测试消息\"");
    }

    /**
     * 测试带提及的文本消息
     */
    @Test
    void testBuildTextWithMention() {
        List<String> userIds = List.of("zhangsan", "lisi");
        List<String> mobiles = List.of("13800138000");

        WebhookMessage message = WebhookMessage.buildTextWithMention(
                "请以下同事注意",
                userIds,
                mobiles
        );
        String json = message.toJson();
        System.out.println("带提及文本消息 JSON: " + json);

        assert json.contains("\"mentioned_list\"");
        assert json.contains("\"zhangsan\"");
    }

    /**
     * 测试 Markdown 消息
     */
    @Test
    void testBuildMarkdown() {
        String markdown = "# 标题\n## 副标题\n- 列表项1\n- 列表项2";
        WebhookMessage message = WebhookMessage.buildMarkdown(markdown);
        String json = message.toJson();
        System.out.println("Markdown 消息 JSON: " + json);

        assert json.contains("\"msgtype\":\"markdown\"");
        assert json.contains("# 标题");
    }

    /**
     * 测试图文消息
     */
    @Test
    void testBuildNews() {
        WebhookMessage.NewsContent.Article article = new WebhookMessage.NewsContent.Article();
        article.setTitle("测试标题")
                .setDescription("测试描述")
                .setUrl("https://example.com")
                .setPicurl("https://example.com/image.jpg");

        WebhookMessage message = WebhookMessage.buildNews(List.of(article));
        String json = message.toJson();
        System.out.println("图文消息 JSON: " + json);

        assert json.contains("\"msgtype\":\"news\"");
        assert json.contains("\"title\":\"测试标题\"");
    }

    /**
     * 测试单条图文消息
     */
    @Test
    void testBuildSingleNews() {
        WebhookMessage message = WebhookMessage.buildSingleNews(
                "标题",
                "描述",
                "https://example.com",
                "https://example.com/image.jpg"
        );
        String json = message.toJson();
        System.out.println("单条图文消息 JSON: " + json);

        assert json.contains("\"articles\":[{");
    }

    /**
     * 测试文件消息
     */
    @Test
    void testBuildFile() {
        WebhookMessage message = WebhookMessage.buildFile("MEDIA_ID_123456");
        String json = message.toJson();
        System.out.println("文件消息 JSON: " + json);

        assert json.contains("\"msgtype\":\"file\"");
        assert json.contains("\"media_id\":\"MEDIA_ID_123456\"");
    }

    /**
     * 测试图片消息
     */
    @Test
    void testBuildImage() {
        WebhookMessage message = WebhookMessage.buildImage("BASE64_DATA", "MD5VALUE");
        String json = message.toJson();
        System.out.println("图片消息 JSON: " + json);

        assert json.contains("\"msgtype\":\"image\"");
        assert json.contains("\"base64\":\"BASE64_DATA\"");
    }

    /**
     * 测试空值处理（null 字段不应该出现在 JSON 中）
     */
    @Test
    void testNullValueHandling() {
        WebhookMessage message = WebhookMessage.buildText("测试");
        // text 中的 mentionedList 和 mentionedMobileList 为空列表，应该被忽略

        String json = message.toJson();
        System.out.println("空值处理 JSON: " + json);

        // 验证没有 null 值
        assert !json.contains("null");
    }

    /**
     * 测试链式调用
     */
    @Test
    void testChaining() {
        WebhookMessage.NewsContent.Article article = new WebhookMessage.NewsContent.Article();
        article.setTitle("标题")
                .setDescription("描述")
                .setUrl("https://example.com")
                .setPicurl("https://example.com/image.jpg");

        assert "标题".equals(article.getTitle());
        assert "描述".equals(article.getDescription());
    }
}
