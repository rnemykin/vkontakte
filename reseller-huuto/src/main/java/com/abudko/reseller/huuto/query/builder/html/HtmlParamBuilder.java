package com.abudko.reseller.huuto.query.builder.html;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.builder.AbstractParamBuilder;
import com.abudko.reseller.huuto.query.builder.ParamBuilderUtils;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.params.SearchParams;

@Component
public class HtmlParamBuilder extends AbstractParamBuilder {

    public String buildQuery(SearchParams params) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Map<String, String> parameters = getParameters(params);
        String query = ParamBuilderUtils.buildRestfulUrl(parameters);

        return query;
    }

    protected Map<String, String> getParameters(SearchParams params) {
        Map<String, String> result = new LinkedHashMap<String, String>();

        result.put("classification", params.getClassification());
        result.put("sellstyle", params.getSellstyle());
        result.put("status", params.getStatus());
        result.put("location", params.getLocation());
        result.put("biddernro", params.getBiddernro());
        result.put("zipcode", params.getZipcode());
        result.put("closingtime", params.getClosingtime());
        result.put("addtime", params.getAddtime());
        result.put("seller_type", params.getSeller_type());
        result.put("sellernro", params.getSellernro());

        String categoryName = params.getWords();
        if (StringUtils.hasLength(categoryName)) {
            Category category = Category.valueOf(categoryName);
            result.put("words", category.getValue());
            
            String categoryStr = getCategoryStr(category.getId());
            if (categoryStr != null) {
                result.put("category", categoryStr);
            }
        }

        return result;
    }
    
    
    private String getCategoryStr(String[] categoryIds) {
        if (categoryIds != null && categoryIds.length > 0) {
            StringBuilder sb = new StringBuilder(categoryIds[0]);
            for (int i = 1; i < categoryIds.length; i++) {
                sb.append("-");
                sb.append(categoryIds[i]);
            }
            return sb.toString();
        }
        
        return null;
    }
}
