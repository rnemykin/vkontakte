package com.abudko.reseller.huuto.query.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class DescriptionKeywordExclusionFilter extends AbstractNotEmptyFilter {

    private static final List<String> EXCLUSION_LIST = Arrays.asList("vakosamettihousut", "tunika", "leggingsit",
            "farkut", "shortsit", "toppi", "mekko", "hame", "t-paita", "sukkahousut", "uimapuku", "TILAUSTUOTE",
            "Shortsihaalari", "Farkkuhaalari", "Lappuhaalari", "Myssy", "Housuhaalari", "Kurahaalari", "Haalarit",
            "Hupullinen haalari");

    @Override
    protected String getValue(ListResponse queryListResponse) {
        String description = queryListResponse.getDescription();
        if (description == null) {
            return null;
        }
        for (String exclusion : EXCLUSION_LIST) {
            if (description.toLowerCase().contains(exclusion.toLowerCase())) {
                return null;
            }
        }

        return description;
    }
}
