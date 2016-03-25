package com.mycompany.plarty.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 3/22/2016.
 */
public class Item {
    private Album album;
    private List<Artist> artists = new ArrayList<>();
    private List<String> available_markets = new ArrayList<>();
    private Integer disc_number;
    private Integer duration_ms;
    private boolean explicit;
    private ExternalID external_ids;
    private ExternalURL external_urls;
    private String href;
    private String id;
    private String name;
    private Integer popularity;
    private String preview_url;
    private Integer track_number;
    private String type;
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<String> getAvailable_markets() {
        return available_markets;
    }

    public void setAvailable_markets(List<String> available_markets) { this.available_markets = available_markets; }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Integer getDisc_number() {
        return disc_number;
    }

    public void setDisc_number(Integer disc_number) {
        this.disc_number = disc_number;
    }

    public Integer getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(Integer duration_ms) {
        this.duration_ms = duration_ms;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public ExternalID getExternal_ids() {
        return external_ids;
    }

    public void setExternal_ids(ExternalID external_ids) {
        this.external_ids = external_ids;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public Integer getTrack_number() {
        return track_number;
    }

    public void setTrack_number(Integer track_number) {
        this.track_number = track_number;
    }

    public ExternalURL getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(ExternalURL external_urls) {
        this.external_urls = external_urls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setUri(String uri) {
        this.uri = uri;
    }
}
