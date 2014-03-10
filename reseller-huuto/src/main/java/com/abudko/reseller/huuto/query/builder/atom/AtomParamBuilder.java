package com.abudko.reseller.huuto.query.builder.atom;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.builder.ParamBuilderUtils;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.params.SearchParams;

/**
 * http://www.huuto.net/keskustelu/show/id/1453889
 * 
 * @author budkal
 * 
 */
@Component
public class AtomParamBuilder implements ParamBuilder {

    public String buildQuery(SearchParams params) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        Map<String, String> parameters = getParameters(params);

        StringBuilder url = new StringBuilder(ParamBuilderUtils.buildUrl(parameters));
        
        String categoryStr = getCategoryStr(params.getWords());
        if (categoryStr != null) {
            url.append(categoryStr);
        }

        return url.toString();
    }

    protected Map<String, String> getParameters(SearchParams params) {
        Map<String, String> result = new LinkedHashMap<String, String>();

        result.put("condition", params.getClassification());
        result.put("type", params.getSellstyle());
        result.put("status", params.getStatus());
        result.put("location", params.getLocation());
        result.put("bidder", params.getBiddernro());
        result.put("closingTime", params.getClosingtime());
        result.put("publishingTime", params.getAddtime());
        result.put("sellerType", params.getSeller_type());
        result.put("seller", params.getSellernro());

        String categoryName = params.getWords();
        if (StringUtils.hasLength(categoryName)) {
            Category category = Category.valueOf(categoryName);
            result.put("q", category.getValue());
        }

        return result;
    }

    private String getCategoryStr(String categoryName) {
        if (StringUtils.hasLength(categoryName)) {
            Category category = Category.valueOf(categoryName);
            String[] categoryIds = category.getId();
            if (categoryIds != null && categoryIds.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < categoryIds.length; i++) {
                    sb.append("&category=");
                    sb.append(categoryIds[i]);
                }
                return sb.toString();
            }
        }

        return null;
    }
}
