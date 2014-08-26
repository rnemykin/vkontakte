package com.abudko.reseller.huuto.query.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class DescriptionKeywordExclusionFilterTest {

    @Test
    public void testFilter() {
        ListResponse listResponseBad = new ListResponse();
        listResponseBad.setDescription("KappAhl joustavat vakosamettihousut, koko 128, -UUSI-EI HV");
        ListResponse listResponseOk = new ListResponse();
        final String descriptionOk = "Reima talvihaalari, koko 98";
        listResponseBad.setDescription(descriptionOk);
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(listResponseBad);
        queryResponses.add(listResponseOk);

        Collection<ListResponse> filtered = new DescriptionKeywordExclusionFilter().apply(queryResponses, null);

        assertEquals(1, filtered.size());
        assertEquals(descriptionOk, filtered.iterator().next().getDescription());
    }

}
