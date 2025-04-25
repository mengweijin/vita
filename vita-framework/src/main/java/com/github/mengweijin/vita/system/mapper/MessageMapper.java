package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vita.system.domain.MessageDO;
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
public interface MessageMapper extends BaseMapper<MessageDO> {

}

