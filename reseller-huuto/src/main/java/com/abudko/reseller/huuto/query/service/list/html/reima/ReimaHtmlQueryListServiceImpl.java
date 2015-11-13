package com.abudko.reseller.huuto.query.service.list.html.reima;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.AbstractQueryListService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

@Component
public class ReimaHtmlQueryListServiceImpl extends AbstractQueryListService {

    @Autowired
    @Qualifier("reimaHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Autowired
    @Qualifier("reimaHtmlListParser")
    private HtmlListParser htmlListParser;
    
    @Autowired
    @Qualifier("reimaPriceRules")
    protected AbstractPriceRules lekmerPriceRules;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Collection<ListResponse> callAndParse(String query) throws URISyntaxException {
        Collection<ListResponse> queryAllResponses = new LinkedHashSet<ListResponse>();

        String pagedURI = getPagedURI(query, 0);
        String responseList = "";
        try {
        	responseList = restTemplate.getForObject(pagedURI, String.class);
        }
        catch (HttpClientErrorException e) {
        	if (HttpStatus.NOT_FOUND_404 != e.getStatusCode().value()) {
        		log.info("Exeption during calling reima list : " + e);	
        	}
        }
        Collection<ListResponse> queryPageResponses = htmlListParser.parse(responseList);

        log.info(String.format("Called [%s], got [%d] responses", pagedURI, queryPageResponses.size()));

        queryAllResponses.addAll(queryPageResponses);
        return queryAllResponses;
    }

    private String getPagedURI(String query, int page) throws URISyntaxException {
        StringBuilder sb = new StringBuilder(QueryConstants.REIMA_HTML_SEARCH_URL);
        sb.append(query);
        return sb.toString();
    }
    
    @Override
    protected QueryItemService getQueryItemService() {
        return queryItemService;
    }
    
    @Override
    protected AbstractPriceRules getPriceRules() {
        return lekmerPriceRules;
    }
}