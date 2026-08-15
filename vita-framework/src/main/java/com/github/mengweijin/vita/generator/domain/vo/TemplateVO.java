package com.github.mengweijin.vita.generator.domain.vo;

import cn.hutool.v7.core.text.CharSequenceUtil;
import com.github.mengweijin.vita.framework.util.ResourceUtils;
import com.github.mengweijin.vita.generator.service.TemplateService;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Data
public class TemplateVO implements Serializable {

    private String id;

    private String name;

    private String content;

    public TemplateVO(ResourceUtils.ResourceInfo resourceInfo) {
        this.id = CharSequenceUtil.subAfter(resourceInfo.getUrl(), TemplateService.TEMPLATE_DIR, false);
        this.name = resourceInfo.getName();
        this.content = resourceInfo.getContent();
    }
}
