package com.abudko.scheduled.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.csvreader.CsvReader;

public class CsvPhotoDataReader implements PhotoDataReader {

    private static final char SEPARATOR = '\'';

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Resource resource;

    public CsvPhotoDataReader(Resource csvFileResource) {
        this.resource = csvFileResource;
    }

    @Override
    public List<PhotoData> read() {
        CsvReader csvReader = null;
        List<PhotoData> photoDataList = new ArrayList<PhotoData>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            csvReader = new CsvReader(inputStreamReader, SEPARATOR);
            csvReader.readHeaders();

            while (csvReader.readRecord()) {
                PhotoData photoData = new PhotoData();

                photoData.setGroupId(csvReader.get("groupId"));
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
