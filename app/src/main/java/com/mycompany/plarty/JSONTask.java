package com.mycompany.plarty;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.mycompany.plarty.TrackModel;
import com.mycompany.plarty.TrackModel.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 3/23/2016.
 */
public class JSONTask extends AsyncTask<String, String, TrackModel> {
    private String command;
    @Override
    protected TrackModel doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            Gson gson = new Gson();
            TrackModel track = new TrackModel();
            JSONObject parentObject = new JSONObject(finalJson);
            JSONObject trackObject = parentObject.getJSONObject("tracks");
            track.setHref(trackObject.getString("href"));
            track.setLimit(trackObject.getInt("limit"));
            track.setNext(trackObject.getString("next"));
            track.setOffset(trackObject.getInt("offset"));
            track.setPrevious(trackObject.getString("previous"));
            track.setTotal(trackObject.getInt("total"));
            JSONArray itemsArray = trackObject.getJSONArray("items");
            List<Item> itemList = new ArrayList<Item>();
            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject tempItemObject = itemsArray.getJSONObject(i);
                itemList.add(gson.fromJson(tempItemObject.toString(), Item.class));
            }
            track.setItemList(itemList);
            return track;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void onPostExecute(TrackModel result) {
        super.onPostExecute(result);
//        Search.resultList = result;
//        System.out.println(result.getItemList().get(0).getUri() + "\n" + result.getItemList().get(0).getName() + "\n" + result.getItemList().get(0).getAlbum().getName());
//        MainActivity.mPlayer.play(result.getItemList().get(0).getUri());
    }
}
