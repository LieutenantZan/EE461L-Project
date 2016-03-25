package com.mycompany.plarty.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 3/22/2016.
 */
public class Album {
    private String album_type;
    private List<String> availableMarketList = new ArrayList<>();
    private ExternalURL external_urls;
    private String href;
    private String id;
    private List<Image> imageList = new ArrayList<>();
    private String name;
    private String type;
    private String uri;

    public String getHref() { return href; }

    public void setHref(String href) { this.href = href; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAlbum_type() {
        return album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public List<String> getAvailableMarketList() {
        return availableMarketList;
    }

    public void setAvailableMarketList(List<String> availableMarketList) { this.availableMarketList = availableMarketList; }

    public ExternalURL getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(ExternalURL external_urls) {
        this.external_urls = external_urls;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) { this.uri = uri; }
}