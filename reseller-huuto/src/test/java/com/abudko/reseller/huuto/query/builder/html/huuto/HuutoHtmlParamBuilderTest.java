package com.abudko.reseller.huuto.query.builder.html.huuto;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.params.SearchParams;

public class HuutoHtmlParamBuilderTest {

    private ParamBuilder builder = new HuutoHtmlParamBuilder();

    private SearchParams searchParams;

    @Before
    public void setup() {
        searchParams = new SearchParams();
    }

    @Test
    public void testPriceMax() throws Exception {
        String priceMax = "232";
        searchParams.setPrice_max(priceMax);

        String query = builder.buildQuery(searchParams);

        assertTrue(query.isEmpty());
    }

    @Test
    public void testPriceMin() throws Exception {
        String priceMin = "22";
        searchParams.setPrice_min(priceMin);

        String query = builder.buildQuery(searchParams);

        assertTrue(query.isEmpty());
    }

    @Test
    public void testLocation() throws Exception {
        String location = "location";
        searchParams.setLocation(location);

        String query = builder.buildQuery(searchParams);

        assertEquals("/location/" + location, query);
    }

    @Test
    public void testZipcode() throws Exception {
        String zipcode = "00000";
        searchParams.setZipcode(zipcode);

        String query = builder.buildQuery(searchParams);

        assertEquals("/zipcode/" + zipcode, query);
    }

    @Test
    public void testStatus() throws Exception {
        String status = "status";
        searchParams.setStatus(status);

        String query = builder.buildQuery(searchParams);

        assertEquals("/status/" + status, query);
    }

    @Test
    public void testClassification() throws Exception {
        String classification = "classification";
        searchParams.setClassification(classification);

        String query = builder.buildQuery(searchParams);

        assertEquals("/classification/" + classification, query);
    }

    @Test
    public void testBiddernro() throws Exception {
        String biddernro = "87528723";
        searchParams.setBiddernro(biddernro);

        String query = builder.buildQuery(searchParams);

        assertEquals("/biddernro/" + biddernro, query);
    }

    @Test
    public void testSellernro() throws Exception {
        String sellernro = "sellernro";
        searchParams.setSellernro(sellernro);

        String query = builder.buildQuery(searchParams);

        assertEquals("/sellernro/" + sellernro, query);
    }

    @Test
    public void testSellerType() throws Exception {
        String sellerType = "sellerType";
        searchParams.setSeller_type(sellerType);

        String query = builder.buildQuery(searchParams);

        assertEquals("/seller_type/" + sellerType, query);
    }

    @Test
    public void testWordsKeyword() throws Exception {
        Category words = Category.SADEHAALARI;
        searchParams.setWords(words.name());

        String query = builder.buildQuery(searchParams);

        assertTrue(query.contains("/words/" + words.getValue()));
    }

    @Test
    public void testWordsCategory() throws Exception {
        Category words = Category.TALVIKENGAT;
        searchParams.setWords(words.name());

        String query = builder.buildQuery(searchParams);

        assertEquals("/words/" + words.getValue() + "/category/728-736", query);
    }

    @Test
    public void testWordsCategoryNotEmpty() throws Exception {
        Category words = Category.SADEHAALARI;
        searchParams.setWords(words.name());

        String query = builder.buildQuery(searchParams);

        assertTrue(query.contains("category"));
    }

    @Test
    public void testBrand() throws Exception {
        String brand = "brand";
        searchParams.setBrand(brand);

        String query = builder.buildQuery(searchParams);

        assertTrue(query.isEmpty());
    }

    @Test
    public void testCategory() throws Exception {
        String category = "category";
        searchParams.setCategory(category);

        String query = builder.buildQuery(searchParams);

        assertTrue(query.isEmpty());
    }

    @Test
    public void testAddtime() throws Exception {
        String addtime = "addtime";
        searchParams.setAddtime(addtime);

        String query = builder.buildQuery(searchParams);

        assertEquals("/addtime/" + addtime, query);
    }

    @Test
    public void testClosingtime() throws Exception {
        String closingtime = "closingtime";
        searchParams.setClosingtime(closingtime);

        String query = builder.buildQuery(searchParams);

        assertEquals("/closingtime/" + closingtime, query);
    }

    @Test
    public void testSellstyle() throws Exception {
        String sellstyle = "sellstyle";
        searchParams.setSellstyle(sellstyle);

        String query = builder.buildQuery(searchParams);

        assertEquals("/sellstyle/" + sellstyle, query);
    }

    @Test
    public void testAll() throws Exception {
        String priceMax = "222";
        searchParams.setPrice_max(priceMax);
        String priceMin = "22";
        searchParams.setPrice_min(priceMin);
        String location = "location";
        searchParams.setLocation(location);
        String zipcode = "00000";
        searchParams.setZipcode(zipcode);
        String status = "status";
        searchParams.setStatus(status);
        String classification = "classification";
        searchParams.setClassification(classification);
        String biddernro = "87528723";
        searchParams.setBiddernro(biddernro);
        String sellernro = "sellernro";
        searchParams.setSellernro(sellernro);
        String sellerType = "sellerType";
        searchParams.setSeller_type(sellerType);
        Category words = Category.TALVIKENGAT;
        searchParams.setWords(words.name());
        String category = "category";
        searchParams.setCategory(category);
        String sellstyle = "sellstyle";
        searchParams.setSellstyle(sellstyle);
        String addtime = "addtime";
        searchParams.setAddtime(addtime);
        String closingtime = "closingtime";
        searchParams.setClosingtime(closingtime);

        String query = builder.buildQuery(searchParams);

        assertEquals("/classification/" + classification + "/sellstyle/" + sellstyle + "/status/" + status
                + "/location/" + location + "/biddernro/" + biddernro + "/zipcode/" + zipcode + "/closingtime/"
                + closingtime + "/addtime/" + addtime + "/seller_type/" + sellerType + "/sellernro/" + sellernro
                + "/words/" + words.getValue() + "/category/728-736", query);
    }
}
