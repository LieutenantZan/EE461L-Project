package com.mycompany.plarty;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
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
        if(MainActivity.host) {
            playlist.add(song);
            setChanged();
            notifyObservers();
            clearChanged();
        } else {
            try {
                MainActivity.client.outputToServer(song);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public Iterator<Song> getSongs(){
        return playlist.iterator();
    }

    public void clearPlaylist(){
        playlist.removeAll(playlist);
    }
}
