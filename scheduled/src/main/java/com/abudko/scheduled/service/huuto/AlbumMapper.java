package com.abudko.scheduled.service.huuto;

import java.util.ArrayList;
import java.util.Collection;
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
    public static final String VÄLI_68_86 = "182291500";
    public static final String VÄLI_92_128 = "182341579";
    public static final String VÄLI_134_164 = "182341609";

    private Map<String, RangeMap<Integer, String>> map = new HashMap<String, RangeMap<Integer, String>>();

    @PostConstruct
    public void init() {
        RangeMap<Integer, String> rangeMapTalvi = TreeRangeMap.create();
        rangeMapTalvi.put(Range.closed(68, 86), TALVI_68_86);
        rangeMapTalvi.put(Range.closed(92, 128), TALVI_92_128);
        rangeMapTalvi.put(Range.closed(134, 164), TALVI_134_164);
        map.put(Category.TALVIHAALARI.name(), rangeMapTalvi);
        map.put(Category.TALVITAKKI.name(), rangeMapTalvi);
        map.put(Category.TALVIHOUSUT.name(), rangeMapTalvi);
        
        RangeMap<Integer, String> rangeMapVäli = TreeRangeMap.create();
        rangeMapVäli.put(Range.closed(68, 86), VÄLI_68_86);
        rangeMapVäli.put(Range.closed(92, 128), VÄLI_92_128);
        rangeMapVäli.put(Range.closed(134, 164), VÄLI_134_164);
        map.put(Category.VALIKAUSIHAALARI.name(), rangeMapVäli);
        map.put(Category.VALIKAUSITAKKI.name(), rangeMapVäli);
        map.put(Category.VALIKAUSIHOUSUT.name(), rangeMapVäli);
    }

    public String getAlbumId(String category, Integer size) {
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
