package com.mycompany.plarty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Search extends Activity {

    public static final String API_URL = "https://api.spotify.com/v1/search?q=";
    private TrackModel resultList;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        ArrayList<TrackModel.Item> arrayOfSongs = new ArrayList<TrackModel.Item>();
        adapter = new SongAdapter(this, arrayOfSongs);
        ListView listView = (ListView) findViewById(R.id.lvSearch);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TrackModel.Item tmp = resultList.getItemList().get(position);
                Song song = new Song(resultList.getItemList().get(position));
                MainActivity.mPlayer.play(song.getURL());
                // TODO - Make it queue the song up
            }
        });
    }

    public void onSearch(View view){
        EditText et = (EditText) findViewById(R.id.searchText);
        String txt = et.getText().toString();
        txt = txt.replaceAll(" ", "%20");
        String url_select = API_URL + txt + "&type=track";
        JSONTask test = new JSONTask();
        test.execute(url_select);
        try {
            resultList = test.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter.clear();
        for (int i = 0; i < resultList.getLimit(); i += 1){
            TrackModel.Item newSong = resultList.getItemList().get(i);
            adapter.add(newSong);
        }
    }
}
