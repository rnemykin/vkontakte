package com.abudko.scheduled.csv;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvPhotoDataLogger implements PhotoDataLogger {

    private static final String PHOTO_ID = "photoId";
    private static final String GROUP_ID = "groupId";
    private static final char SEPARATOR = '\'';

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Resource resource;

    public CsvPhotoDataLogger(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void dump(Map<String, String> photoIdGroupIdMap) {
        CsvWriter csvWriter = null;
        try {
            csvWriter = new CsvWriter(new FileWriter(resource.getFile().getAbsolutePath()), SEPARATOR);
            csvWriter.writeRecord(new String[] { PHOTO_ID, GROUP_ID });
            Set<Entry<String, String>> entrySet = photoIdGroupIdMap.entrySet();
            for (Entry<String, String> e : entrySet) {
                String photoId = e.getKey();
                String groupId = e.getValue();
                csvWriter.writeRecord(new String[] { photoId, groupId });
            }
        } catch (FileNotFoundException fe) {
            log.error("Error in parsing csv", fe);
        } catch (IOException ioe) {
            log.error("Error in parsing csv", ioe);
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }

    @Override
    public Map<String, String> read() {
        CsvReader csvReader = null;
        Map<String, String> photoIdGroupIdMap = new LinkedHashMap<String, String>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            csvReader = new CsvReader(inputStreamReader, SEPARATOR);
            csvReader.readHeaders();

            while (csvReader.readRecord()) {
                photoIdGroupIdMap.put(csvReader.get(PHOTO_ID), csvReader.get(GROUP_ID));
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

        return photoIdGroupIdMap;
    }
}
