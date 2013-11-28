package com.abudko.scheduled.service.huuto;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

@Component
public class AlbumMapper {
    
    public static final String GROUP_ID = "60966965";
    public static final String TALVI_68_86 = "182291496";
    public static final String TALVI_92_128 = "182269995";
    public static final String TALVI_134_164 = "182328502";
    
    private Map<String, RangeMap<Integer, String>> map = new HashMap<String, RangeMap<Integer, String>>();
    
    @PostConstruct
    public void init() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 86), TALVI_68_86);
        rangeMap.put(Range.closed(92, 128), TALVI_92_128);
        rangeMap.put(Range.closed(134, 164), TALVI_134_164);
        map.put(Category.TALVIHAALARI.name(), rangeMap);
    }
    
    public String getAlbumId(String category, Integer size) {
        RangeMap<Integer, String> rangeMap = map.get(category);
        if (rangeMap == null) {
            return null;
        }
        return rangeMap.get(size);
    }
}
