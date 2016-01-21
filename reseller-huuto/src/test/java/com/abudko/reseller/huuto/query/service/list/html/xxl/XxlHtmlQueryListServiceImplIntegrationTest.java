package com.abudko.reseller.huuto.query.service.list.html.xxl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class XxlHtmlQueryListServiceImplIntegrationTest {

	@Autowired
	@Qualifier("simpleHtmlParamBuilder")
	private ParamBuilder paramBuilder;

	@Autowired
	@Qualifier("xxlHtmlQueryListServiceImpl")
	private QueryListService queryService;

	@Test
	@Ignore
	public void testScan() throws Exception {
		SearchParams searchParams = getSearchParams();
		String query = getQuery(searchParams);

		Collection<ListResponse> results = queryService.search(query, searchParams);
	}

	private String getQuery(SearchParams searchParams)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return paramBuilder.buildQuery(searchParams);
	}

	private SearchParams getSearchParams() {
		SearchParams searchParams = new SearchParams();
		searchParams.setWords("talvihaalari");

		return searchParams;
	}
}
