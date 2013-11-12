package com.abudko.scheduled.csv;

import java.util.Map;

public interface PhotoDataLogger {

    void dump(Map<String, String> photoIdGroupIdMap, String dumpFileLocation);
    
    Map<String, String> read(String dumpFileLocation);

}