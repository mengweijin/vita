package com.github.mengweijin.quickboot.jpa.idgenerator;

import com.github.mengweijin.quickboot.framework.util.TimestampIdUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author Meng Wei Jin
 * 自定义ID生成规则
 * @date Create in 2019-07-27 22:32
 **/
public class TimestampIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        return TimestampIdUtils.timestampId();
    }
}
