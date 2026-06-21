package com.github.mengweijin.vita.framework.webhook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业微信 Webhook 消息模型
 * 支持文本、Markdown、图片、图文、文件、模板卡片等所有消息类型
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * // 构建文本消息
 * WebhookMessage message = WebhookMessage.buildText("这是一条测试消息");
 *
 * // 构建 Markdown 消息
 * WebhookMessage message = WebhookMessage.buildMarkdown("# 标题\n内容");
 *
 * // 构建图文消息
 * WebhookMessage.NewsContent.Article article = new WebhookMessage.NewsContent.Article();
 * article.setTitle("标题")
 *        .setDescription("描述")
 *        .setUrl("https://example.com")
 *        .setPicurl("https://example.com/image.jpg");
 * WebhookMessage message = WebhookMessage.buildNews(List.of(article));
 *
 * // 转换为 JSON
 * String json = message.toJson();
 * }</pre>
 *
 * @author mengweijin
 * @since 2026/6/20
 * @see WebhookService
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookMessage {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 消息类型：text|markdown|image|news|file|template_card
     */
    private String msgtype;

    /**
     * 文本消息内容
     */
    private TextContent text;

    /**
     * Markdown 消息内容
     */
    private MarkdownContent markdown;

    /**
     * 图片消息内容
     */
    private ImageContent image;

    /**
     * 图文消息内容
     */
    private NewsContent news;

    /**
     * 文件消息内容
     */
    private FileContent file;

    /**
     * 模板卡片消息内容
     */
    private TemplateCardContent templateCard;

    /**
     * 将消息转换为 JSON 字符串
     *
     * @return JSON 字符串
     */
    public String toJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert message to JSON", e);
        }
    }

    // ==================== 内部类：各种消息内容 ====================

    /**
     * 文本消息内容
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TextContent {
        /**
         * 文本内容，最长不超过2048个字节，utf8编码
         */
        private String content;

        /**
         * 被提及人的userid列表（最多100个）
         */
        private List<String> mentionedList;

        /**
         * 被提及人的手机号列表（最多100个）
         */
        private List<String> mentionedMobileList;

        public TextContent() {
            this.mentionedList = new ArrayList<>();
            this.mentionedMobileList = new ArrayList<>();
        }
    }

    /**
     * Markdown 消息内容
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MarkdownContent {
        /**
         * Markdown 内容，最长不超过4096个字节，utf8编码
         */
        private String content;
    }

    /**
     * 图片消息内容
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ImageContent {
        /**
         * 图片文件的base64编码，最大不超过2M
         */
        private String base64;

        /**
         * 图片名称（带后缀）
         */
        private String md5;
    }

    /**
     * 图文消息内容
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NewsContent {
        /**
         * 图文消息，一个图文消息支持1到8条图文
         */
        private List<Article> articles;

        public NewsContent() {
            this.articles = new ArrayList<>();
        }

        @Data
        @Accessors(chain = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Article {
            /**
             * 标题，不超过128个字节，超过会自动截断
             */
            private String title;

            /**
             * 描述，不超过512个字节，超过会自动截断
             */
            private String description;

            /**
             * 点击后跳转的链接
             */
            private String url;

            /**
             * 图文消息的图片链接，支持JPG、PNG格式
             */
            private String picurl;
        }
    }

    /**
     * 文件消息内容
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FileContent {
        /**
         * 文件id，通过上传接口获取
         */
        private String mediaId;
    }

    /**
     * 模板卡片消息内容（简化版）
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TemplateCardContent {
        /**
         * 卡片类型
         */
        private String cardType;

        /**
         * 卡片主标题
         */
        private MainTitle mainTitle;

        @Data
        @Accessors(chain = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class MainTitle {
            private String title;
            private String desc;
        }
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 构建文本消息
     *
     * @param content 文本内容
     * @return WebhookMessage
     */
    public static WebhookMessage buildText(String content) {
        WebhookMessage message = new WebhookMessage();
        message.setMsgtype("text");

        TextContent text = new TextContent();
        text.setContent(content);
        message.setText(text);

        return message;
    }

    /**
     * 构建文本消息（带提及功能）
     *
     * @param content          文本内容
     * @param mentionedList    被提及人的userid列表
     * @param mentionedMobiles 被提及人的手机号列表
     * @return WebhookMessage
     */
    public static WebhookMessage buildTextWithMention(String content, List<String> mentionedList, List<String> mentionedMobiles) {
        WebhookMessage message = buildText(content);

        if (mentionedList != null && !mentionedList.isEmpty()) {
            message.getText().setMentionedList(mentionedList);
        }
        if (mentionedMobiles != null && !mentionedMobiles.isEmpty()) {
            message.getText().setMentionedMobileList(mentionedMobiles);
        }

        return message;
    }

    /**
     * 构建 Markdown 消息
     *
     * @param content Markdown 内容
     * @return WebhookMessage
     */
    public static WebhookMessage buildMarkdown(String content) {
        WebhookMessage message = new WebhookMessage();
        message.setMsgtype("markdown");

        MarkdownContent markdown = new MarkdownContent();
        markdown.setContent(content);
        message.setMarkdown(markdown);

        return message;
    }

    /**
     * 构建图片消息
     *
     * @param base64 图片 base64 编码
     * @param md5    图片 MD5
     * @return WebhookMessage
     */
    public static WebhookMessage buildImage(String base64, String md5) {
        WebhookMessage message = new WebhookMessage();
        message.setMsgtype("image");

        ImageContent image = new ImageContent();
        image.setBase64(base64);
        image.setMd5(md5);
        message.setImage(image);

        return message;
    }

    /**
     * 构建图文消息
     *
     * @param articles 图文列表
     * @return WebhookMessage
     */
    public static WebhookMessage buildNews(List<NewsContent.Article> articles) {
        WebhookMessage message = new WebhookMessage();
        message.setMsgtype("news");

        NewsContent news = new NewsContent();
        news.setArticles(articles);
        message.setNews(news);

        return message;
    }

    /**
     * 构建单条图文消息
     *
     * @param title       标题
     * @param description 描述
     * @param url         链接
     * @param picurl      图片链接
     * @return WebhookMessage
     */
    public static WebhookMessage buildSingleNews(String title, String description, String url, String picurl) {
        NewsContent.Article article = new NewsContent.Article();
        article.setTitle(title)
                .setDescription(description)
                .setUrl(url)
                .setPicurl(picurl);

        return buildNews(List.of(article));
    }

    /**
     * 构建文件消息
     *
     * @param mediaId 文件 ID
     * @return WebhookMessage
     */
    public static WebhookMessage buildFile(String mediaId) {
        WebhookMessage message = new WebhookMessage();
        message.setMsgtype("file");

        FileContent file = new FileContent();
        file.setMediaId(mediaId);
        message.setFile(file);

        return message;
    }
}
