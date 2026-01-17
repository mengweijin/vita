package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.domain.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Post Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface PostMapper extends BaseVitaMapper<PostDO, PostVO> {

}

