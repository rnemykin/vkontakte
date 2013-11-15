package com.abudko.reseller.huuto.query.builder;

import java.util.Map;

import com.abudko.reseller.huuto.query.params.SearchParams;

public abstract class AbstractParamBuilder implements ParamBuilder {

    protected abstract Map<String, String> getParameters(SearchParams params);
}
