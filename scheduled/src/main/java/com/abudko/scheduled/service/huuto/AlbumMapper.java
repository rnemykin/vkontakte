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

    public static final String GROUP_ID = "60966965";
    public static final String USED_ALBUM_ID = "198051037";
    public static final String TALVI_68_86 = "182291496";
    public static final String TALVI_92_128 = "182269995";
    public static final String TALVI_134_164 = "182328502";
    public static final String VALI_68_86 = "182291500";
    public static final String VALI_92_128 = "182341579";
    public static final String VALI_134_164 = "182341609";
    public static final String TALVIKENGAT_20_27 = "183417603";
    public static final String TALVIKENGAT_28_37 = "183417638";
    public static final String VALILENKKARIT_20_27 = "183417888";
    public static final String VALILENKKARIT_28_37 = "183417910";
    public static final String VILLA_68_92 = "183428281";
    public static final String VILLA_98_128 = "183430684";
    public static final String SADE_68_92 = "184180187";
    public static final String SADE_98_134 = "184180277";

    private Map<String, RangeMap<Integer, String>> map = new HashMap<String, RangeMap<Integer, String>>();

    @PostConstruct
    public void init() {
        setupTalvi();
        setupVali();
        setupTalviKengat();
        setupValiKengat();
        setupVillaFleece();
        setupSadeKura();
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
    
    private void setupVali() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 86), VALI_68_86);
        rangeMap.put(Range.closed(92, 128), VALI_92_128);
        rangeMap.put(Range.closed(134, 164), VALI_134_164);
        map.put(Category.VALIKAUSIHAALARI.name(), rangeMap);
        map.put(Category.VALIKAUSITAKKI.name(), rangeMap);
        map.put(Category.VALIKAUSIHOUSUT.name(), rangeMap);
    }

    private void setupTalviKengat() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(20, 27), TALVIKENGAT_20_27);
        rangeMap.put(Range.closed(28, 37), TALVIKENGAT_28_37);
        map.put(Category.TALVIKENGAT.name(), rangeMap);
    }
    
    private void setupValiKengat() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(20, 27), VALILENKKARIT_20_27);
        rangeMap.put(Range.closed(28, 37), VALILENKKARIT_28_37);
        map.put(Category.VALIKAUSIKENGAT.name(), rangeMap);
        map.put(Category.LENKKARIT.name(), rangeMap);
    }

    private void setupVillaFleece() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 92), VILLA_68_92);
        rangeMap.put(Range.closed(98, 128), VILLA_98_128);
        map.put(Category.VILLAHAALARI.name(), rangeMap);
    }
    
    private void setupSadeKura() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 92), SADE_68_92);
        rangeMap.put(Range.closed(98, 134), SADE_98_134);
        map.put(Category.SADEHAALARI.name(), rangeMap);
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
