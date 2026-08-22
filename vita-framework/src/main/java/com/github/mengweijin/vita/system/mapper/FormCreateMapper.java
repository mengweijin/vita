package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.FormCreateDO;
import com.github.mengweijin.vita.system.domain.vo.FormCreateVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表单管理表 FormCreate Mapper
 *
 * @author mengweijin
 * @since 2026-08-22
 */
@Mapper
public interface FormCreateMapper extends BaseVitaMapper<FormCreateDO, FormCreateVO> {

}

