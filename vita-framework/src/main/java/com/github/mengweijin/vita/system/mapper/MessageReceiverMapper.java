package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  MessageReceiver Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface MessageReceiverMapper extends BaseMapper<MessageReceiverDO> {

    /**
     * 自定义分页
     * @param page page
     * @param message MessageVO
     * @return IPage
     */
    IPage<MessageVO> page(Page<MessageVO> page, @Param("p") MessageVO message);
}

