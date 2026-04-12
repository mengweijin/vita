package ${package}.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import ${package}.domain.entity.PostDO;
import ${package}.domain.vo.PostVO;
import ${package}.mapper.PostMapper;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.service.UserPostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collection;

/**
 * $!{table.comment} ${entityName} Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
@AllArgsConstructor
public class ${entityName}Service extends BaseVitaService<${entityName}Mapper, ${entityName}DO, ${entityName}VO> {

    private ${entityName}Service ${entityPropertyName}Service;

    @Override
    public LambdaQueryWrapper<${entityName}DO> buildQueryWrapper(${entityName}DO ${entityPropertyName}) {
        LambdaQueryWrapper<${entityName}DO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(${entityPropertyName}.getId() != null, ${entityName}DO::getId, ${entityPropertyName}.getId());
    #foreach($field in ${entityFields})
        #set($upperFirstName = $hutoolStrUtil.upperFirst(${field.propertyName}))
        #if(${field.columnType}=="STRING")
        wrapper.eq(StrUtil.isNotBlank(${entityPropertyName}.get$upperFirstName()), ${entityName}DO::get$upperFirstName, ${entityPropertyName}.get$upperFirstName());
        #else
        wrapper.eq(${entityPropertyName}.get$upperFirstName() != null, ${entityName}DO::get$upperFirstName, ${entityPropertyName}.get$upperFirstName());
        #end
    #end
        wrapper.gt(${entityPropertyName}.getStartCreateTime() != null, ${entityName}DO::getCreateTime, ${entityPropertyName}.getStartCreateTime());
        wrapper.le(${entityPropertyName}.getEndCreateTime() != null, ${entityName}DO::getCreateTime, ${entityPropertyName}.getEndCreateTime());
        return wrapper;
    }
}
