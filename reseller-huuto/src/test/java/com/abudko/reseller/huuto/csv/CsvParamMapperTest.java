package com.abudko.reseller.huuto.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.abudko.reseller.huuto.query.mapper.csv.CsvParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;

public class CsvParamMapperTest {

    private CsvParamMapper queryParamMapper;

    @Before
    public void setup() throws FileNotFoundException {
        queryParamMapper = new CsvParamMapper(new ClassPathResource("/csv/test.csv"));
    }

    @Test
    public void testGetSearchParamsBuyerId() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("BuyerId1", queryParams.getBiddernro());
    }

    @Test
    public void testGetSearchParamsSellerId() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("SellerId", queryParams.getSellernro());
    }

    @Test
    public void testGetSearchParamsLocation() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("Location", queryParams.getLocation());
    }

    @Test
    public void testGetSearchParamsKeyword() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("Keyword", queryParams.getWords());
    }

    @Test
    public void testGetSearchParamsCategory() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("Category1-Category2", queryParams.getCategory());
    }

    @Test
    public void testGetSearchParamsCategoryEnum() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);
        
        assertEquals("TALVIHAALARI", queryParams.getCategoryenum());
    }

    @Test
    public void testGetSearchParamsClassification() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("NEW", queryParams.getClassification());
    }

    @Test
    public void testGetSearchParamsCreatedTimePassed() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("4", queryParams.getAddtime());
    }

    @Test
    public void testGetSearchParamsCreatedTimePassedNegativeValue() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(2);

        assertEquals("-30000", queryParams.getAddtime());
    }

    @Test
    public void testGetSearchParamsClosedTimeLeft() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("5", queryParams.getClosingtime());
    }

    @Test
    public void testGetSearchParamsMinPrice() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("6", queryParams.getPrice_min());
    }

    @Test
    public void testGetSearchParamsMaxPrice() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("7", queryParams.getPrice_max());
    }

    @Test
    public void testGetSearchParamsSellerType() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("PRIVATE", queryParams.getSeller_type());
    }

    @Test
    public void testGetSearchParamsSellStyle() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("AUCTION", queryParams.getSellstyle());
    }

    @Test
    public void testGetSearchParamsStatus() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("OPENED", queryParams.getStatus());
    }

    @Test
    public void testGetSearchParamsZipcode() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(0);

        assertEquals("Zipcode", queryParams.getZipcode());
    }

    @Test
    public void testGetSearchParamsSeveralRows() throws IOException {
        int size = queryParamMapper.getSearchParams().size();
        assertTrue("size: " + size, size > 1);
    }

    @Test
    public void testGetSearchParamsReadSecondRow() throws IOException {
        SearchParams queryParams = queryParamMapper.getSearchParams().get(1);

        assertEquals("BuyerId2", queryParams.getBiddernro());
    }
}
