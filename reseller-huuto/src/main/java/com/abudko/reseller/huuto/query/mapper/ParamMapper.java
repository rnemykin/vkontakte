package com.abudko.reseller.huuto.query.mapper;

import java.util.List;

import com.abudko.reseller.huuto.query.params.SearchParams;

public interface ParamMapper {
    
    List<SearchParams> getSearchParams();

}
