package com.abudko.scheduled.vkontakte;

public class UploadedPhoto {
    
    private String server;
    
    private String aid;
    
    private String hash;
    
    private String gid;
    
    private String photos_list;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPhotos_list() {
        return photos_list;
    }

    public void setPhotos_list(String photos_list) {
        this.photos_list = photos_list;
    }

    @Override
    public String toString() {
        return "UploadedPhoto [server=" + server + ", aid=" + aid + ", hash=" + hash + ", gid=" + gid
                + ", photos_list=" + photos_list + "]";
    }
}
