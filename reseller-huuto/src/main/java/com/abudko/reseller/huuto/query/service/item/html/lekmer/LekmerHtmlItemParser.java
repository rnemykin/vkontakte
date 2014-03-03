package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;

@Component
public class LekmerHtmlItemParser implements HtmlItemParser {

    @Override
    public ItemResponse parse(String html) {
        ItemResponse response = new ItemResponse();

        ItemStatus itemStatus = parseItemStatus(html);
        response.setItemStatus(itemStatus);

        return response;
    }

    private ItemStatus parseItemStatus(String html) {
        int prodCount = 0;
        Pattern pattern = Pattern.compile("class=\"productTotalCount\">(\\d+)");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            prodCount = Integer.parseInt(matcher.group(1));
        }

        return prodCount == 0 ? ItemStatus.CLOSED : ItemStatus.OPENED;
    }
}