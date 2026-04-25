package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import com.github.mengweijin.vita.system.domain.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Oss Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface FileMapper extends BaseVitaMapper<FileDO, FileVO> {

}

