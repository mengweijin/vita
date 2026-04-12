package ${package}.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
#if("$!{baseEntity}" != "")
import ${baseEntity};
#else
#end
import ${package}.domain.bo.${entityName}BO;
import ${package}.domain.vo.${entityName}VO;
import com.github.mengweijin.vita.system.domain.bo.DeptBO;
import com.github.mengweijin.vita.system.domain.vo.DeptVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * $!{table.comment}
 *
 * @author ${author}
 * @since ${date}
 */
@AutoMappers({
        @AutoMapper(target = ${entityName}BO.class),
        @AutoMapper(target = ${entityName}VO.class),
})
@Data
#if("$!{baseEntity}" != "")
@EqualsAndHashCode(callSuper = true)
#else
@EqualsAndHashCode(callSuper = false)
#end
@TableName("${table.name}")
#if("$!{baseEntityName}" != "")
public class ${entityName}DO extends ${baseEntityName} {
#else
public class ${entityName}DO implements Serializable {

    private static final long serialVersionUID = 1L;
#end
#foreach($field in ${entityFields})

#if("$!field.comment" != "")
    /**
    * ${field.comment}
    */
#end
#if(${field.keyFlag})
    @TableId("${field.annotationColumnName}")
#end
## 乐观锁注解
#if(${field.versionField})
    @Version
#end
## 逻辑删除注解
#if(${field.logicDeleteField})
    @TableLogic
#end
    private ${field.propertyType} ${field.propertyName};
#end
}
