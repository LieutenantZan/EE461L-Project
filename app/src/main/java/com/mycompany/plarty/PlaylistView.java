package com.mycompany.plarty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class PlaylistView extends Activity {

    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_view);

        ArrayList<Song> arrayOfSongs = new ArrayList<Song>();
        adapter = new SongAdapter(this, arrayOfSongs);
        ListView listView = (ListView) findViewById(R.id.lvPlaylist);
        listView.setAdapter(adapter);
        Iterator songs = MainActivity.playlist.getSongs();
        while(songs.hasNext()){
            Song newSong = (Song) songs.next();
            adapter.add(newSong);
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                TrackModel.Item tmp = resultList.getItemList().get(position);
//                Song song = new Song(resultList.getItemList().get(position));
//                //MainActivity.mPlayer.play(song.getURL());
//                MainActivity.playlist.queueSong(song);
//            }
//        });
    }

    public void onSearch(View view){
        adapter.clear();
        Iterator songs = MainActivity.playlist.getSongs();
        while(songs.hasNext()){
            Song newSong = (Song) songs.next();
            adapter.add(newSong);
        }
    }
}
