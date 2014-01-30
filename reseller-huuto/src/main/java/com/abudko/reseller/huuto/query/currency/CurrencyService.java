package com.abudko.reseller.huuto.query.currency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(50);

    private static final String URL = "http://rate-exchange.appspot.com/currency?from=EUR&to=RUB";

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("currencyRate")
    public BigDecimal getRate() {
        BigDecimal rate = DEFAULT_RATE;

        try {
            rate = restTemplate.getForObject(URL, RateResponse.class).getRate();
        } catch (Throwable e) {
            log.error(String.format("Unable to extract currency rate, returning DEFAULT '%s'", DEFAULT_RATE), e);
            rate = getRateCbr();
        }
        
        return rate;
    }
    
    
    private void getRateCbr() {
        try {
        System.out.println("Hello World!");
        
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx");
        httpPost.addHeader("SOAPAction", "http://web.cbr.ru/GetCursOnDate");

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        sb.append("\n");
        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\"");
        sb.append("\n");
        sb.append("xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\"");
        sb.append("\n");
        sb.append("xmlns:tns=\"http://web.cbr.ru/\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\"");
        sb.append("\n");
        sb.append("xmlns:soap12=\"http://schemas.xmlsoap.org/wsdl/soap12/\" xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\"");
        sb.append("\n");
        sb.append("xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        sb.append("\n");
        sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
        sb.append("\n");
        sb.append("<SOAP-ENV:Body>");
        sb.append("\n");
        sb.append("<tns:GetCursOnDate xmlns:tns=\"http://web.cbr.ru/\">");
        sb.append("\n");
        sb.append("<tns:On_date>2014-01-27</tns:On_date>");
        sb.append("\n");
        sb.append("</tns:GetCursOnDate>");
        sb.append("\n");
        sb.append("</SOAP-ENV:Body>");
        sb.append("\n");
        sb.append("</SOAP-ENV:Envelope>");
        
        httpPost.setEntity(new StringEntity(sb.toString()));
        httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
        HttpResponse hr = client.execute(httpPost);
         
        String responseNoSpaces = EntityUtils.toString(hr.getEntity(), "UTF-8").replace(" ", "");
        
        final String euroSubstring = "Евро</Vname><Vnom>1</Vnom><Vcurs>";
        String substring = responseNoSpaces.substring(responseNoSpaces.indexOf(euroSubstring) + euroSubstring.length());
        String rate = substring.substring(0, substring.indexOf("<")); 
        

    } catch (Exception ex) {
        
    }
    }
}
