package com.abudko.scheduled.service.huuto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

@Component
public class AlbumMapper {

    public static final String GROUP_ID = "78393244";
    public static final String TALVI_68_86 = "203810597";
    public static final String TALVI_92_128 = "203810612";
    public static final String TALVI_134_164 = "203810636";
    public static final String TALVIKENGAT_20_27 = "203810656";
    public static final String TALVIKENGAT_28_37 = "203809455";

    private Map<String, RangeMap<Integer, String>> map = new HashMap<String, RangeMap<Integer, String>>();

    @PostConstruct
    public void init() {
        setupTalvi();
        setupTalviKengat();
    }
    
    private void setupTalvi() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 86), TALVI_68_86);
        rangeMap.put(Range.closed(92, 128), TALVI_92_128);
        rangeMap.put(Range.closed(134, 164), TALVI_134_164);
        map.put(Category.TALVIHAALARI.name(), rangeMap);
        map.put(Category.TALVITAKKI.name(), rangeMap);
        map.put(Category.TALVIHOUSUT.name(), rangeMap);
    }
    
    private void setupTalviKengat() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(20, 27), TALVIKENGAT_20_27);
        rangeMap.put(Range.closed(28, 37), TALVIKENGAT_28_37);
        map.put(Category.TALVIKENGAT.name(), rangeMap);
    }

    public String getAlbumId(String category, ListResponse listResponse) {
        Integer size = 1;
        if (listResponse.hasManySizes()) {
            List<String> sizes = listResponse.getItemResponse().getSizes();
            Collections.shuffle(sizes);
            size = Integer.valueOf(sizes.get(0));
        }
        else {
            size = Integer.valueOf(listResponse.getSize());
        }
        RangeMap<Integer, String> rangeMap = map.get(category);
        if (rangeMap == null) {
            return null;
        }
        return rangeMap.get(size);
    }

    public Collection<String> getAlbumIds(String category) {
        RangeMap<Integer, String> rangeMap = map.get(category);
        if (rangeMap == null) {
            return new ArrayList<String>();
        }
        return rangeMap.asMapOfRanges().values();
    }
}
