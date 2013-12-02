package com.abudko.reseller.huuto.query.service.item.atom;

import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Text;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.feed.atom.Person;

@Component
public class AtomXmlItemParser {

    private static final String PRICE = "buyNowPrice";
    private static final String LOCATION = "city";
    private static final String CONDITION = "condition";
    private static final String STATUS = "itemStatus";
    private static final String IMG_SRC = "image-normal";
    private static final String IMG_SUFFIX = "-m.jpg";

    @SuppressWarnings("unchecked")
    public ItemResponse parse(Feed atomXmlResponse) {
        ItemResponse response = new ItemResponse();

        String imgSrc = parseImgSrc(atomXmlResponse);
        response.setImgBaseSrc(imgSrc);

        String seller = parseSeller(atomXmlResponse);
        response.setSeller(seller);

        List<Element> foreignMarkup = (List<Element>) atomXmlResponse.getForeignMarkup();

        String condition = parseCondition(foreignMarkup);
        response.setCondition(condition);

        String itemStatus = parseItemStatus(foreignMarkup);
        response.setItemStatus(itemStatus);

        String location = parseLocation(foreignMarkup);
        response.setLocation(location);

        String price = parsePrice(foreignMarkup);
        response.setPrice(price);

        return response;
    }

    private String parseCondition(List<Element> foreignMarkup) {
        String condition = "";

        for (Element e : foreignMarkup) {
            if (CONDITION.equals(e.getName())) {
                Attribute attribute = (Attribute) e.getAttributes().get(0);
                condition = attribute.getValue();
            }
        }

        return condition;
    }

    private String parseItemStatus(List<Element> foreignMarkup) {
        String itemStatus = "";
        
        for (Element e : foreignMarkup) {
            if (STATUS.equals(e.getName())) {
                itemStatus = e.getText();
            }
        }
        
        return itemStatus;
    }

    private String parseLocation(List<Element> foreignMarkup) {
        String location = "";

        for (Element e : foreignMarkup) {
            List<?> content = e.getContent();
            for (Object object2 : content) {
                if (object2 instanceof Element) {
                    Element el = (Element) object2;
                    if (LOCATION.equals(el.getName())) {
                        Text elData = (Text) el.getContent().get(0);
                        location = elData.getValue();
                    }
                }
            }
        }

        return location;
    }

    private String parsePrice(List<Element> foreignMarkup) {
        String fullPrice = "";

        for (Element e : foreignMarkup) {
            List<?> content = e.getContent();
            for (Object object2 : content) {
                if (object2 instanceof Element) {
                    Element el = (Element) object2;
                    if (PRICE.equals(el.getName())) {
                        Text elData = (Text) el.getContent().get(0);
                        fullPrice = elData.getValue();
                    }
                }
            }
        }

        return fullPrice.replace(",", ".");
    }

    private String parseImgSrc(Feed atomXmlResponse) {
        String imgSrc = "";
        List<?> otherLinks = atomXmlResponse.getOtherLinks();
        for (Object object : otherLinks) {
            if (object instanceof Link) {
                Link link = (Link) object;
                if (IMG_SRC.equals(link.getRel())) {
                    imgSrc = link.getHref();
                }
            }
        }
        return formatImgSrc(imgSrc);
    }
    
    private String formatImgSrc(String imgSrc) {
        String formattedImgSrc = imgSrc.replace(IMG_SUFFIX, "");
        return formattedImgSrc;
    }

    private String parseSeller(Feed atomXmlResponse) {
        List<?> authors = atomXmlResponse.getAuthors();
        if (authors.isEmpty() == false) {
            Person seller = (Person) atomXmlResponse.getAuthors().get(0);
            return seller.getName();
        }
        
        return "";
    }
}
