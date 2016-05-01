package com.mycompany.plarty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Playlist extends Observable {
    private ArrayList<Song> playlist;
    int index;

    Playlist(){
        playlist = new ArrayList<Song>();
        index = 0;
    }

    public Song getNext(){
        if (index >= playlist.size()){
            return null;
        } else {
            Song next = playlist.get(index);
            index += 1;
            return next;
        }
    }

    public void queueSong(Song song){
        playlist.add(song);
        setChanged();
        notifyObservers();
        clearChanged();
    }

    private void sendSongToQueue(Song song) throws JSONException{
        JSONObject json = new JSONObject();
        try {
            json.put("songName", song.getSong());
            json.put("songURL", song.getURL());
            json.put("artist", song.getArtist());
            json.put("albumName", song.getAlbum());
            json.put("albumArtURL", song.getAlbumArtURL());
        } catch (JSONException e){
            e.printStackTrace();
        }

        //TODO - send JSONObject.toString() in the client to the server
    }

    public Iterator<Song> getSongs(){
        return playlist.iterator();
    }

    public void clearPlaylist(){
        playlist.removeAll(playlist);
    }
}
