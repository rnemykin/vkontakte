package com.abudko.scheduled.rules.xxl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;

@Component
public class XxlItemValidityRules extends AbstractItemValidityRules {
    
	@Autowired
    @Qualifier("xxlHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;
    
    @Autowired
    @Qualifier("xxlHtmlQueryListServiceImpl")
    private QueryListService queryListService;
    
    @Autowired
    @Qualifier("simpleHtmlParamBuilder")
    private ParamBuilder paramBuilder;

    @Override
    public String getIdPrefix() {
        return HtmlParserConstants.XXL_ID_PREFIX;
    }

    @Override
    public QueryItemService getQueryItemService() {
        return queryItemService;
    }

    @Override
    protected boolean isValidInternal(String id) {
        Collection<ListResponse> responses = null;
        SearchParams searchParams = getSearchParams(id);
        String query = null;
        try {
            query = paramBuilder.buildQuery(searchParams);
            responses = queryListService.search(query, searchParams);
        } catch (UnsupportedEncodingException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | URISyntaxException e) {
            log.error(String.format("Unable to call lekmer search list,  query '%s', searchParams '%s'", query, searchParams));
        }
        
        return responses != null && !responses.isEmpty();
    }
    
    private SearchParams getSearchParams(String id) {
        SearchParams searchParams = new SearchParams();
        searchParams.setWords(id);
        
        return searchParams;
    }
}
