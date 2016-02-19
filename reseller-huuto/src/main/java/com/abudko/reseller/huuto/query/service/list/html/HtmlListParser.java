package com.abudko.reseller.huuto.query.service.list.html;

import java.util.Collection;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

public interface HtmlListParser {

    Collection<ListResponse> parse(String htmlResponse);

}