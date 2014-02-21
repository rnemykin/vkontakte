package com.abudko.reseller.huuto.query.service.item.html;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;

public interface HtmlItemParser {

    ItemResponse parse(String html);

}