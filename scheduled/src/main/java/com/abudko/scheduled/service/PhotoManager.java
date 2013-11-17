package com.abudko.scheduled.service;

public interface PhotoManager {

    void publish(String csvResourcePath, String dumpFileLocation, String token) throws InterruptedException;

}