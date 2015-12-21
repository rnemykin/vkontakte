package com.abudko.reseller.huuto.query.service.list.json;

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
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.item.json.JsonItemResponse;
import com.abudko.reseller.huuto.query.service.list.AbstractQueryListService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class JsonQueryListServiceImpl extends AbstractQueryListService {
	
	private static final String PAGE_PARAM = "/page/";
	private static final String LIMIT_PARAM = "/limit/";

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    @Qualifier("jsonQueryItemServiceImpl")
    private QueryItemService queryItemService;
    
	@Override
	public Collection<ListResponse> callAndParse(String query) throws URISyntaxException {
		Collection<ListResponse> queryAllResponses = new LinkedHashSet<ListResponse>();

		URI pagedURI = getPagedURI(query, 1, 500);
		JsonListResponse responseList = restTemplate.getForObject(pagedURI, JsonListResponse.class);
		Collection<ListResponse> queryPageResponses = responseList.getListResponses();

		String info = String.format("Called [%s], got [%d] responses", pagedURI, queryPageResponses.size());
		log.info(info);

		queryAllResponses.addAll(queryPageResponses);

		return queryAllResponses;
	}
    
    private URI getPagedURI(String query, final int page, final int limit) throws URISyntaxException {
        StringBuilder sb = new StringBuilder(QueryConstants.JSON_QUERY_URL);
        sb.append(query);
        sb.append(PAGE_PARAM);
        sb.append(page);
        sb.append(LIMIT_PARAM);
        sb.append(limit);
        URI uri = new URI(sb.toString());

        return uri;
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
