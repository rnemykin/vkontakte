package com.abudko.reseller.huuto.query.builder;

import java.lang.reflect.InvocationTargetException;

import com.abudko.reseller.huuto.query.params.SearchParams;

public interface ParamBuilder {

    String buildQuery(SearchParams params) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException;
}
