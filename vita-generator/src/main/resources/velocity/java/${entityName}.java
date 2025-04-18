package ${package}.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
#if("$!{baseEntity}" != "")
import ${baseEntity};
#else
import java.io.Serializable;
#end

/**
 * $!{table.comment}
 *
 * @author ${author}
 * @since ${date}
 */
@Data
#if("$!{baseEntity}" != "")
@EqualsAndHashCode(callSuper = true)
#else
@EqualsAndHashCode(callSuper = false)
#end
@TableName("${table.name}")
#if("$!{baseEntityName}" != "")
public class ${entityName} extends ${baseEntityName} {
#else
public class ${entityName} implements Serializable {

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
