package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.MessageDO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Message Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface MessageMapper extends BaseVitaMapper<MessageDO, MessageVO> {

}

