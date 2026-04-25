package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.domain.vo.MessageReceiverVO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * MessageReceiver Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface MessageReceiverMapper extends BaseVitaMapper<MessageReceiverDO, MessageReceiverVO> {

    /**
     * 自定义分页
     *
     * @param page    page
     * @param message MessageVO
     * @return IPage
     */
    IPage<MessageVO> page(IPage<MessageVO> page, @Param("p") MessageVO message);
}

