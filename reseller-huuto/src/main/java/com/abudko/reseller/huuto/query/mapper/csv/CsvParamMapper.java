package com.abudko.reseller.huuto.query.mapper.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.mapper.ParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.csvreader.CsvReader;

public class CsvParamMapper implements ParamMapper {

    private Logger log = LoggerFactory.getLogger(getClass());

    private static final char SEPARATOR = ',';

    private final Resource resource;

    public CsvParamMapper(Resource csvResource) {
        this.resource = csvResource;
    }

    public List<SearchParams> getSearchParams() {
        CsvReader csvReader = null;
        List<SearchParams> queryParamsList = new ArrayList<SearchParams>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            csvReader = new CsvReader(inputStreamReader, SEPARATOR);
            csvReader.readHeaders();

            while (csvReader.readRecord()) {
                SearchParams searchParams = new SearchParams();

                Headers[] headers = Headers.values();

                for (Headers header : headers) {
                    readProperty(csvReader, header.name(), searchParams);
                }

                queryParamsList.add(searchParams);
            }
        } catch (IllegalAccessException iae) {
            log.error("Error in parsing csv", iae);
        } catch (InvocationTargetException ite) {
            log.error("Error in parsing csv", ite);
        } catch (FileNotFoundException fe) {
            log.error("Error in parsing csv", fe);
        } catch (IOException ioe) {
            log.error("Error in parsing csv", ioe);
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }

        return filter(queryParamsList);
    }
    
    private List<SearchParams> filter(List<SearchParams> params) {
        List<SearchParams> filtered = new ArrayList<SearchParams>();
        
        for (SearchParams searchParams : params) {
            String biddernro = searchParams.getBiddernro();
            if (StringUtils.isEmpty(biddernro) || !biddernro.contains("//")) {
                filtered.add(searchParams);
            }
        }
        
        return filtered;
    }

    private void readProperty(CsvReader reader, String property, SearchParams destination) throws IOException,
            IllegalAccessException, InvocationTargetException {
        String value = reader.get(property);
        BeanUtils.setProperty(destination, property, value);
    }
}
