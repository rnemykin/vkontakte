package com.abudko.reseller.huuto.query.filter;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class ImageFilter extends AbstractNotEmptyFilter {

    @Override
    protected String getValue(ListResponse queryListResponse) {
        return queryListResponse.getImgBaseSrc();
    }
}
