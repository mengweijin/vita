package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.NoticeDO;
import com.github.mengweijin.vita.system.domain.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Notice Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface NoticeMapper extends BaseVitaMapper<NoticeDO, NoticeVO> {

}

