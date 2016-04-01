package com.mycompany.plarty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Antonio San Pedro on 4/1/2016.
 */
public class ImageTask extends AsyncTask<String, String, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        HttpURLConnection connection = null;
        Bitmap albumCover = null;
        try{
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            albumCover = BitmapFactory.decodeStream(stream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        return albumCover;
    }

    protected void onPostExecute(Bitmap result) {
        //TODO implement an update for the images that will get recognized in time
        MainActivity.mPlayer.chosenTracks.get(0).getItem().getAlbum().setBitmap(result);
    }
}
