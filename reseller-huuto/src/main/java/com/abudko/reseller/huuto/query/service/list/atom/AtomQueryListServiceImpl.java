package com.abudko.reseller.huuto.query.service.list.atom;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.rometools.rome.feed.atom.Feed;

@Component
public class AtomQueryListServiceImpl extends AbstractQueryListService {
    
    private static final int MAX_ITEMS_ON_PAGE = 20;
    
    private static final String PAGE_PARAM = "&page=";

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private AtomXmlListParser atomXmlListParser;
    
    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Override
    public Collection<ListResponse> callAndParse(String query) throws URISyntaxException {
        Collection<ListResponse> queryAllResponses = new LinkedHashSet<ListResponse>();
        boolean morePages = false;
        
        int page = 1;
        do {
            URI pagedURI = getPagedURI(query, page++);
            
            log.info(String.format("Called [%s]", pagedURI));
            
            Feed atomXmlResponse = restTemplate.getForObject(pagedURI, Feed.class);
            Collection<ListResponse> queryPageResponses = atomXmlListParser.parse(atomXmlResponse);
            
            log.info(String.format("Got [%d] responses", queryPageResponses.size()));
            
            queryAllResponses.addAll(queryPageResponses);
            
            morePages = morePages(queryPageResponses);
        }
        while (morePages);

        return queryAllResponses;
    }
    
    private URI getPagedURI(String query, int page) throws URISyntaxException {
        StringBuilder sb = new StringBuilder(QueryConstants.ATOM_QUERY_URL);
        sb.append(query);
        sb.append(PAGE_PARAM);
        sb.append(page);
        URI uri = new URI(sb.toString());
        
        return uri;
    }
    
    private boolean morePages(Collection<ListResponse> queryPageResponses) {
        return queryPageResponses.size() == MAX_ITEMS_ON_PAGE;
    }
    
    @Override
    protected QueryItemService getQueryItemService() {
        return queryItemService;
    }

    @Override
    protected AbstractPriceRules getPriceRules() {
        return this.defaultPriceRules;
    }
}