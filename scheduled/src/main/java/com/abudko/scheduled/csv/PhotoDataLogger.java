package com.abudko.scheduled.csv;

import java.util.Map;

public interface PhotoDataLogger {

    void dump(Map<String, String> photoIdGroupIdMap);
    
    Map<String, String> read();

}