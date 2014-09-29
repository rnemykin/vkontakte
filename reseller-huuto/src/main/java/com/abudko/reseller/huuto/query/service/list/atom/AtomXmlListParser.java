package com.abudko.reseller.huuto.query.service.list.atom;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Text;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.SizeParser;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;

@Component
public class AtomXmlListParser {

    private static final String CURRENT_PRICE = "currentPrice";
    private static final String FULL_PRICE = "buyNowPrice";
    private static final String IMG_SUFFIX = "-s.jpg";
    
    public Collection<ListResponse> parse(Feed atomXmlResponse) {
        Collection<ListResponse> responses = new LinkedHashSet<ListResponse>();

        List<Entry> entries = atomXmlResponse.getEntries();
        for (Entry entry : entries) {
            ListResponse queryResponse = new ListResponse();

            String description = parseDescription(entry);
            queryResponse.setDescription(description);

            String currentPrice = parseCurrentPrice(entry);
            queryResponse.setCurrentPrice(currentPrice);

            String fullPrice = parseFullPrice(entry);
            queryResponse.setFullPrice(fullPrice);

            String itemUrl = parseItemUrl(entry);
            queryResponse.setItemUrl(itemUrl);

            String imgBaseSrc = parseImgSrc(entry);
            queryResponse.setImgBaseSrc(imgBaseSrc);

            String size = parseSize(description);
            queryResponse.setSize(size);

            String brand = parseBrand(description);
            queryResponse.setBrand(brand);

            responses.add(queryResponse);
        }

        return responses;
    }

    private String parseDescription(Entry entry) {
        return entry.getTitleEx().getValue();
    }
    
    private String parseCurrentPrice(Entry entry) {
        List<Element> foreignMarkup = (List<Element>) entry.getForeignMarkup();
        String fullPrice = "";

        for (Element e : foreignMarkup) {
            List content = e.getContent();
            for (Object object2 : content) {
                if (object2 instanceof Element) {
                    Element el = (Element) object2;
                    if (CURRENT_PRICE.equals(el.getName())) {
                        Text elData = (Text) el.getContent().get(0);
                        fullPrice = elData.getValue();
                    }
                }
            }
        }

        return fullPrice.replace(",", ".");
    }
    
    private String parseFullPrice(Entry entry) {
        List<Element> foreignMarkup = (List<Element>) entry.getForeignMarkup();
        String fullPrice = "";
        
        for (Element e : foreignMarkup) {
            List content = e.getContent();
            for (Object object2 : content) {
                if (object2 instanceof Element) {
                    Element el = (Element) object2;
                    if (FULL_PRICE.equals(el.getName())) {
                        Text elData = (Text) el.getContent().get(0);
                        fullPrice = elData.getValue();
                    }
                }
            }
        }
        
        return fullPrice.replace(",", ".");
    }
    
    private String parseItemUrl(Entry entry) {
        return formatItemUrl(entry.getId());
    }
    
    private String parseImgSrc(Entry entry) {
        if (entry.getOtherLinks().isEmpty()) {
            return "";
        }
        Link link = (Link) entry.getOtherLinks().get(0);
        return formatImgSrc(link.getHref());
    }

    private String formatImgSrc(String imgSrc) {
        String formattedImgSrc = imgSrc.replace(IMG_SUFFIX, "");
        return formattedImgSrc;
    }
    
    private String formatItemUrl(String itemUrl) {
        int index = itemUrl.lastIndexOf("/");
        return itemUrl.substring(index + 1);
    }
    
    private String parseSize(String description) {
        return SizeParser.getSize(description);
    }

    private String parseBrand(String description) {
        Brand brand = Brand.getBrandFrom(description);
        if (brand != null) {
            return brand.getFullName();
        }
        return null;
    }
}
