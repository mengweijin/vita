package com.github.mengweijin.vita.framework.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author mengweijin
 * @since 2026/6/28
 */
public final class BigNumberModule extends SimpleModule {

    public BigNumberModule() {
        this.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        this.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
        this.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
    }
}
