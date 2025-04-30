package com.github.mengweijin.vita.framework.jackson.translation.strategy;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用户图像翻译
 *
 * @author mengweijin
 * @since 2023/5/20
 */
@Component
@AllArgsConstructor
public class UserAvatarTranslationStrategy implements ITranslationStrategy {

    private UserService userService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.USER_ID_TO_AVATAR;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof Long id) {
            return userService.getAvatarById(id);
        }
        return null;
    }
}
