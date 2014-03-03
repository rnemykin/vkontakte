package com.abudko.reseller.huuto.query.service.list.html;

import java.util.Collection;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

public interface HtmlListParser {

    public abstract Collection<ListResponse> parse(String htmlResponse);

}