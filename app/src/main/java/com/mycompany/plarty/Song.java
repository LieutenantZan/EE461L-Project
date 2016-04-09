package com.mycompany.plarty;

import android.graphics.Bitmap;

import java.util.concurrent.ExecutionException;

public class Song {
    private String songName;
    private String songURL;
    private String artist;
    private String albumName;
    private Bitmap albumArt;


    public Song(){
        this.songName = null;
        this.songURL = null;
        this.artist = null;
        this.albumName = null;
        this.albumArt = null;
    }

    public Song(TrackModel.Item track){
        this.songName = track.getName();
        this.songURL = track.getUri();
        this.artist = track.getArtists().get(0).getName();
        this.albumName = track.getAlbum().getName();
        BitmapDownloadTask bit = new BitmapDownloadTask();
        bit.execute(track.getAlbum().getImageList().get(1).getUrl());
        try {
            this.albumArt = bit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getSong(){
        return this.songName;
    }

    public String getArtist(){
        return this.artist;
    }

    public String getAlbum(){
        return this.albumName;
    }

    public Bitmap getAlbumArt(){
        return this.albumArt;
    }

    public String getURL(){
        return this.songURL;
    }

}
