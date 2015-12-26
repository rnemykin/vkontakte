package com.abudko.reseller.huuto.query.service.list.html.xxl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.AbstractQueryListService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;
import com.abudko.reseller.huuto.query.webclient.city.CityWebClient;

@Component
public class XxlHtmlQueryListServiceImpl extends AbstractQueryListService {

    @Autowired
    @Qualifier("xxlHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Autowired
    @Qualifier("xxlHtmlListParser")
    private HtmlListParser htmlListParser;

    @Autowired
    @Qualifier("add20PriceRules")
    private AbstractPriceRules priceRules;

    @Autowired
    private CityWebClient webClient;

    @Override
    public Collection<ListResponse> callAndParse(String query) throws URISyntaxException {
        Collection<ListResponse> queryAllResponses = new LinkedHashSet<ListResponse>();

        URI pagedURI = getPagedURI(query, 0);
        String html = webClient.call(pagedURI.toString());

        Collection<ListResponse> queryPageResponses = htmlListParser.parse(html);

        log.info(String.format("Called [%s], got [%d] responses", pagedURI, queryPageResponses.size()));

        queryAllResponses.addAll(queryPageResponses);
        return queryAllResponses;
    }

    private URI getPagedURI(String keyword, int page) throws URISyntaxException {
        String encodedKeyword = encodeKeyword(keyword);
        String encodedQuery = QueryConstants.XXL_HTML_SEARCH_URL + encodedKeyword;
        URI uri = new URI(encodedQuery.replace(" ", "+"));

        return uri;
    }

    private String encodeKeyword(String keyword) {
        try {
            return URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected QueryItemService getQueryItemService() {
        return queryItemService;
    }

    @Override
    protected AbstractPriceRules getPriceRules() {
        return priceRules;
    }
}