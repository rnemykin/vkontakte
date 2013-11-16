package com.abudko.scheduled.csv;

public class PhotoData {

    private String groupId;

    private String albumId;

    private String fileLocation;

    private String description;

    private String photoId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "PhotoData [groupId=" + groupId + ", albumId=" + albumId + ", fileLocation=" + fileLocation
                + ", description=" + description + ", photoId=" + photoId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((fileLocation == null) ? 0 : fileLocation.hashCode());
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhotoData other = (PhotoData) obj;
        if (albumId == null) {
            if (other.albumId != null)
                return false;
        } else if (!albumId.equals(other.albumId))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (fileLocation == null) {
            if (other.fileLocation != null)
                return false;
        } else if (!fileLocation.equals(other.fileLocation))
            return false;
        if (groupId == null) {
            if (other.groupId != null)
                return false;
        } else if (!groupId.equals(other.groupId))
            return false;
        if (photoId == null) {
            if (other.photoId != null)
                return false;
        } else if (!photoId.equals(other.photoId))
            return false;
        return true;
    }
}
