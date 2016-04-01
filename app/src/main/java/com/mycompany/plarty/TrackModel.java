package com.mycompany.plarty;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 3/22/2016.
 */
public class TrackModel {
    private String href;
    private List<Item> itemList;
    private boolean trackSelected = false;
    private Item item;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;
    private Integer rating;

    public void chooseTrack(int index){
        item = itemList.get(index);
        itemList.clear();
        trackSelected = true;
    }
    public int getRating(){ return rating; }
    public void upVote(){ rating += 1; }
    public void downVote(){ rating -= 1; }
    public Item getItem() { return item; }
    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }
    public List<Item> getItemList() {
        return itemList;
    }
    public void setItemList(List<Item> itemList) { this.itemList = itemList; }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    public String getNext() {
        return next;
    }
    public void setNext(String next) {
        this.next = next;
    }
    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    public String getPrevious() {
        return previous;
    }
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    public class Item {
        private Album album;
        private List<Artist> artists = new ArrayList<>();
        private boolean explicit;
        private String href;
        private String id;
        private String name;
        private Integer popularity;
        private String type;
        private String uri;

        public Album getAlbum() { return album; }
        public void setAlbum(Album album) { this.album = album; }
        public List<Artist> getArtists() { return artists; }
        public void setArtists(List<Artist> artists) { this.artists = artists; }
        public boolean isExplicit() {
            return explicit;
        }
        public void setExplicit(boolean explicit) {
            this.explicit = explicit;
        }
        public Integer getPopularity() {
            return popularity;
        }
        public void setPopularity(Integer popularity) {
            this.popularity = popularity;
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

        public class Album {
            private String href;
            private String id;
            private List<Image> imageList = new ArrayList<>();
            private String name;
            private String type;
            private String uri;
            private Bitmap bitmap;

            public String getHref() { return href; }
            public void setHref(String href) { this.href = href; }
            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
            public List<Image> getImageList() { return imageList; }
            public void setImageList(List<Image> imageList) { this.imageList = imageList; }
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public String getType() { return type; }
            public void setType(String type) { this.type = type; }
            public String getUri() { return uri; }
            public void setUri(String uri) { this.uri = uri; }
            public Bitmap getBitmap(){ return bitmap; }
            public void setBitmap(Bitmap bitmap){ this.bitmap = bitmap; }

            public class Image {
                private Integer height;
                private String url;
                private Integer width;

                public Integer getHeight() {
                    return height;
                }
                public void setHeight(Integer height) {
                    this.height = height;
                }
                public String getUrl() {
                    return url;
                }
                public void setUrl(String url) {
                    this.url = url;
                }
                public Integer getWidth() {
                    return width;
                }
                public void setWidth(Integer width) { this.width = width; }
            }
        }

        public class Artist {
            private String href;
            private String id;
            private String name;
            private String type;
            private String uri;

            public String getHref() { return href; }
            public void setHref(String href) { this.href = href; }
            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public String getType() { return type; }
            public void setType(String type) { this.type = type; }
            public String getUri() { return uri; }
            public void setUri(String uri) { this.uri = uri; }
        }
    }

}