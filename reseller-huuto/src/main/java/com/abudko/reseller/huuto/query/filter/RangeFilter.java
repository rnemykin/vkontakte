package com.abudko.reseller.huuto.query.filter;

import org.springframework.util.StringUtils;

public abstract class RangeFilter implements SearchResultFilter {
    
    protected double parseMin(String minString) {
        if (StringUtils.hasLength(minString)) {
            return Double.parseDouble(minString);
        }
        return 0;
    }

    protected double parseMax(String maxString) {
        if (StringUtils.hasLength(maxString)) {
            return Double.parseDouble(maxString);
        }
        return Integer.MAX_VALUE;
    }
}
