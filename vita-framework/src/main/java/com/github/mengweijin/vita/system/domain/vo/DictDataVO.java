package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * DictData VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataVO extends DictDataDO {

    /**
     * 字典类型编码
     */
    @Translation(translateType = ETranslateType.DICT_TYPE_ID_TO_CODE, field = "typeId")
    private String code;
}
