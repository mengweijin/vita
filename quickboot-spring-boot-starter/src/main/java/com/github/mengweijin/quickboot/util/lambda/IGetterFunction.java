package com.github.mengweijin.quickboot.util.lambda;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Deprecated
@FunctionalInterface
public interface IGetterFunction<T> extends Serializable {

    /**
     * get
     * @param t t
     */
    void get(T t);
}
