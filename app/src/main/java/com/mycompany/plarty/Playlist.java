package com.mycompany.plarty;

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

    public Iterator<Song> getSongs(){
        return playlist.iterator();
    }
}
