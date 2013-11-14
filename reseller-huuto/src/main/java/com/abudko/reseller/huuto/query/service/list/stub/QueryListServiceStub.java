package com.abudko.reseller.huuto.query.service.list.stub;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.list.AbstractQueryListService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class QueryListServiceStub extends AbstractQueryListService {

    @Override
    public Collection<ListResponse> callAndParse(String query) throws URISyntaxException{
        return getStubResponses();
    }

    private Collection<ListResponse> getStubResponses() {
        Collection<ListResponse> responses = new ArrayList<ListResponse>();

        ListResponse response1 = new ListResponse();
        response1.setDescription("description" + new Random().nextInt(10000));
        response1.setBrand("Reima");
        response1.setSize("86");
        response1.setFullPrice("50");
        response1.setItemUrl("http://www.itemUrl.com/id1");
        response1.setCurrentPrice("26");

        ListResponse response2 = new ListResponse();
        response2.setDescription("description" + new Random().nextInt(10000));
        response2.setBrand("Jonathan");
        response2.setSize("92");
        response2.setFullPrice("99");
        response2.setItemUrl("http://www.itemUrl.com/id2");
        response2.setCurrentPrice("53");

        responses.add(response1);
        responses.add(response2);

        return responses;
    }

}
