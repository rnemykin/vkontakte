package com.abudko.scheduled.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.csvreader.CsvReader;

@Component
public class CsvPhotoDataReader implements PhotoDataReader {
	
	private static final Pattern DEFAULT_VALUES_PATTERN = Pattern.compile("\\((\\d+)\\|(\\d+)\\)");

    private static final char SEPARATOR = '\'';

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<PhotoData> read(String csvResourcePath) {
        CsvReader csvReader = null;
        List<PhotoData> photoDataList = new ArrayList<>();
        try {
            csvReader = new CsvReader(new ClassPathResource(csvResourcePath).getInputStream(), SEPARATOR,
                    Charset.forName("UTF-8"));
            csvReader.readHeaders();
            
            String defaultGroupId = null;
            String defaultAlbumId = null;

            while (csvReader.readRecord()) {
                PhotoData photoData = new PhotoData();
                
                String rowStart = csvReader.get("albumId");
                
                if (rowStart != null && rowStart.startsWith("(")) {
                	defaultGroupId = getDefaultGroupId(rowStart);
                	defaultAlbumId = getDefaultAlbumId(rowStart);
                	continue;
                }

                if (shouldSkipRow(csvReader)) {
                    continue;
                }

                photoData.setGroupId(defaultGroupId);
                photoData.setAlbumId(StringUtils.isEmpty(rowStart) ? defaultAlbumId : rowStart);
                photoData.setFileResource(new ClassPathResource("/photos/" + csvReader.get("file")));
                photoData.setDescription(csvReader.get("description"));

                photoDataList.add(photoData);
            }
        } catch (FileNotFoundException fe) {
            log.error("Error in parsing csv", fe);
        } catch (IOException ioe) {
            log.error("Error in parsing csv", ioe);
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }

        return photoDataList;
    }

    private boolean shouldSkipRow(CsvReader csvReader) throws IOException {
        String albumId = csvReader.get("albumId");
        if (albumId == null || albumId.contains("//") || csvReader.get("description").contains("//")
                || albumId.contains("\\") || csvReader.get("description").contains("\\")) {
            return true;
        }

        return false;
    }
    
    String getDefaultGroupId(String row) {
    	Matcher matcher = DEFAULT_VALUES_PATTERN.matcher(row);
    	if (matcher.find()) {
    		return matcher.group(1);
    	}
    	
    	return null;
    }
    
    String getDefaultAlbumId(String row) {
    	Matcher matcher = DEFAULT_VALUES_PATTERN.matcher(row);
    	while (matcher.find()) {
    		return matcher.group(2);
    	}
    	
    	return null;
    }

}
