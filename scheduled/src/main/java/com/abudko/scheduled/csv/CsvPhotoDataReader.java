package com.abudko.scheduled.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.csvreader.CsvReader;

@Component
public class CsvPhotoDataReader implements PhotoDataReader {

    private static final char SEPARATOR = '\'';

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<PhotoData> read(String csvResourcePath) {
        CsvReader csvReader = null;
        List<PhotoData> photoDataList = new ArrayList<PhotoData>();
        try {
            csvReader = new CsvReader(new ClassPathResource(csvResourcePath).getInputStream(), SEPARATOR,
                    Charset.forName("UTF-8"));
            csvReader.readHeaders();

            while (csvReader.readRecord()) {
                PhotoData photoData = new PhotoData();

                String groupId = csvReader.get("groupId");
                if (groupId == null || groupId.contains("//")) {
                    continue;
                }

                photoData.setGroupId(groupId);
                photoData.setAlbumId(csvReader.get("albumId"));
                photoData.setFileLocation(csvReader.get("file"));
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

}
