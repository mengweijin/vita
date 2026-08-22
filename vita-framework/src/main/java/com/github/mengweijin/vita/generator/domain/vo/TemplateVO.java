package com.github.mengweijin.vita.generator.domain.vo;

import cn.hutool.v7.core.text.CharSequenceUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.util.ResourceUtils;
import com.github.mengweijin.vita.generator.service.TemplateService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Data
@NoArgsConstructor
public class TemplateVO implements Serializable {

    private String id;

    private String parentId;

    private String name;

    private String content;

    public TemplateVO(ResourceUtils.ResourceInfo resourceInfo) {
        this.id = CharSequenceUtil.subAfter(resourceInfo.getUrl(), TemplateService.TEMPLATE_DIR, false);
        this.parentId = CharSequenceUtil.subBefore(this.id, Const.SLASH, true);
        this.name = resourceInfo.getName();
        this.content = resourceInfo.getContent();
    }

    public TemplateVO(String id, String parentId, String name, String content) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.content = content;
    }
}
