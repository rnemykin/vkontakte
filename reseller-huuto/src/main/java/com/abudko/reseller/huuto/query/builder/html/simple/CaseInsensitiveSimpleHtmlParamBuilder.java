package com.abudko.reseller.huuto.query.builder.html.simple;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.params.SearchParams;

@Component
public class CaseInsensitiveSimpleHtmlParamBuilder implements ParamBuilder {

    @Override
    public String buildQuery(SearchParams params) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        
        if (params == null || params.getWords() == null) {
            return null;
        }

        return params.getWords();
    }

}
