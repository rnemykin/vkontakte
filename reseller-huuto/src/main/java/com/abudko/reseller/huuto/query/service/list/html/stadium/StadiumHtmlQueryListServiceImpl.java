package com.abudko.reseller.huuto.query.service.list.html.stadium;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.AbstractQueryListService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

@Component
public class StadiumHtmlQueryListServiceImpl extends AbstractQueryListService {

    @Autowired
    @Qualifier("stadiumHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Autowired
    @Qualifier("stadiumHtmlListParser")
    private HtmlListParser htmlListParser;
    
    @Autowired
    @Qualifier("reimaPriceRules")
    protected AbstractPriceRules priceRules;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Collection<ListResponse> callAndParse(String query) throws URISyntaxException {
        Collection<ListResponse> queryAllResponses = new LinkedHashSet<>();

        URI pagedURI = getPagedURI(query, 0);
        String responseList = restTemplate.getForObject(pagedURI, String.class);
        Collection<ListResponse> queryPageResponses = htmlListParser.parse(responseList);

        log.info(String.format("Called [%s], got [%d] responses", pagedURI, queryPageResponses.size()));

        queryAllResponses.addAll(queryPageResponses);
        return queryAllResponses;
    }

    private URI getPagedURI(String query, int page) throws URISyntaxException {
        StringBuilder sb = new StringBuilder(QueryConstants.STADIUM_HTML_SEARCH_URL);
        String[] split = query.split(" ");
        for (int i = 0; i < split.length; i++) {
            sb.append(encodeKeyword(split[i]));
            sb.append(" ");
        }
        
        String encodedQuery = sb.toString().trim();
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