package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.system.domain.entity.MessageDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * Message VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageVO extends MessageDO {

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 消息接收者用户ID
     */
    private Long userId;

    /**
     * 是否已查看。[Y, N]
     */
    private String viewed;

    /**
     * 查看时间
     */
    private LocalDateTime viewedTime;

}
