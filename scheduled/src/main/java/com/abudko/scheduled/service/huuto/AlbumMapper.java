package com.abudko.scheduled.service.huuto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

@Component
public class AlbumMapper implements IAlbumMapper {

    public static final String GROUP_ID = "78393244";
    public static final String TALVI_68_80 = "203810597";
    public static final String TALVI_86_98 = "204197229";
    public static final String TALVI_104_128 = "203810612";
    public static final String TALVI_134_164 = "203810636";
    public static final String TALVIKENGAT_20_25 = "203810656";
    public static final String TALVIKENGAT_26_31 = "204193543";
    public static final String TALVIKENGAT_32_40 = "203809455";
    public static final String VILLA_68_92 = "204025638";
    public static final String VILLA_98_128 = "204025679";
    public static final String REIMA = "204197598";
    public static final String TICKET = "204197609";
    public static final String VIKING = "204197626";

    private Map<String, RangeMap<Integer, String>> map = new HashMap<String, RangeMap<Integer, String>>();
    
    private Map<Brand, String> brandMap = new HashMap<Brand, String>();

    @PostConstruct
    public void init() {
        setupTalvi();
        setupTalviKengat();
        setupVillaFleece();
        setupBrandMap();
    }
    
    private void setupTalvi() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 80), TALVI_68_80);
        rangeMap.put(Range.closed(86, 98), TALVI_86_98);
        rangeMap.put(Range.closed(104, 128), TALVI_104_128);
        rangeMap.put(Range.closed(134, 164), TALVI_134_164);
        map.put(Category.TALVIHAALARI.name(), rangeMap);
        map.put(Category.TALVITAKKI.name(), rangeMap);
        map.put(Category.TALVIHOUSUT.name(), rangeMap);
    }
    
    private void setupTalviKengat() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(20, 25), TALVIKENGAT_20_25);
        rangeMap.put(Range.closed(26, 31), TALVIKENGAT_26_31);
        rangeMap.put(Range.closed(32, 40), TALVIKENGAT_32_40);
        map.put(Category.TALVIKENGAT.name(), rangeMap);
    }
    
    private void setupVillaFleece() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(68, 92), VILLA_68_92);
        rangeMap.put(Range.closed(98, 128), VILLA_98_128);
        map.put(Category.VILLAHAALARI.name(), rangeMap);
    }
    
    private void setupBrandMap() {
        brandMap.put(Brand.REIMA, REIMA);
        brandMap.put(Brand.REIMATEC, REIMA);
        brandMap.put(Brand.TICKET, TICKET);
        brandMap.put(Brand.VIKING, VIKING);
    }

    public String getAlbumId(String category, ListResponse listResponse) {
        Integer size = 1;
        if (listResponse.hasManySizes()) {
            List<String> sizes = listResponse.getItemResponse().getSizes();
            Collections.shuffle(sizes);
            String s = extractSize(sizes.get(0));
            size = Integer.valueOf(s);
        }
        else {
            String s = extractSize(listResponse.getSize());
            size = Integer.valueOf(s);
        }
        RangeMap<Integer, String> rangeMap = map.get(category);
        if (rangeMap == null) {
            return null;
        }
        return rangeMap.get(size);
    }
    
    private String extractSize(String sizeStr) {
        if (sizeStr != null && sizeStr.contains("-")) {
            return sizeStr.substring(0, sizeStr.indexOf("-"));
        }
        
        return sizeStr;
    }
    
    public String getAlbumIdForBrand(ListResponse listResponse) {
        if (listResponse.getBrand() == null) {
            return null;
        }
        
        try {
            Brand brand = Brand.getBrandFrom(listResponse.getBrand().toUpperCase());
            return brandMap.get(brand);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Collection<String> getAlbumIds(String category) {
        RangeMap<Integer, String> rangeMap = map.get(category);
        if (rangeMap == null) {
            return new ArrayList<String>();
        }
        return rangeMap.asMapOfRanges().values();
    }
    
    public String getGroupId() {
        return GROUP_ID;
    }
}
