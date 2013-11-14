package com.abudko.reseller.huuto.query.service.list;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Collection;

import com.abudko.reseller.huuto.query.params.SearchParams;

public interface QueryListService {

    Collection<ListResponse> search(String query, SearchParams queryParams) throws UnsupportedEncodingException,
            URISyntaxException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}