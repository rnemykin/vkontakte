package com.abudko.scheduled.csv;

import org.springframework.core.io.Resource;

public class PhotoData {

    private String groupId;

    private String albumId;

    private Resource fileLocationResource;

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

    public Resource getFileResource() {
        return fileLocationResource;
    }

    public void setFileResource(Resource fileLocationResource) {
        this.fileLocationResource = fileLocationResource;
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
        return "PhotoData [groupId=" + groupId + ", albumId=" + albumId + ", fileLocationResource="
                + fileLocationResource + ", description=" + description + ", photoId=" + photoId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((fileLocationResource == null) ? 0 : fileLocationResource.hashCode());
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
        if (fileLocationResource == null) {
            if (other.fileLocationResource != null)
                return false;
        } else if (!fileLocationResource.equals(other.fileLocationResource))
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
