package com.mycompany.plarty;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Song {
    private String songName;
    private String songURL;
    private String artist;
    private String albumName;
    private String albumArtURL;
    private Bitmap albumArt;


    public Song(){
        this.songName = null;
        this.songURL = null;
        this.artist = null;
        this.albumName = null;
        this.albumArt = null;
        this.albumArtURL = null;
    }

    public Song(TrackModel.Item track){
        this.songName = track.getName();
        this.songURL = track.getUri();
        this.artist = track.getArtists().get(0).getName();
        this.albumName = track.getAlbum().getName();
        this.albumArtURL = track.getAlbum().getImageList().get(1).getUrl();
        BitmapDownloadTask bit = new BitmapDownloadTask();
        bit.execute(albumArtURL);
        try {
            this.albumArt = bit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Song(String input){
        try {
            JSONObject json = new JSONObject(input);
            this.songName = json.getString("songName");
            this.songURL = json.getString("songURL");
            this.artist = json.getString("artist");
            this.albumName = json.getString("albumName");
            this.albumArtURL = json.getString("albumArtURL");
            BitmapDownloadTask bit = new BitmapDownloadTask();
            bit.execute(albumArtURL);
            try {
                this.albumArt = bit.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        catch (JSONException e){

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

    public String getAlbumArtURL(){
        return this.albumArtURL;
    }

    public String getURL(){
        return this.songURL;
    }

}
