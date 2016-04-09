package com.mycompany.plarty;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HostSongReceiveTask extends AsyncTask<Song, String, Song>{
    private Context context;

    public HostSongReceiveTask(Context context) {
        this.context = context;
    }

    @Override
    protected Song doInBackground(Song... params) {
        try {

            /**
             * Create a server socket and wait for client connections. This
             * call blocks until a connection is accepted from a client
             */
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            /**
             * If this code is reached, a client has connected and transferred data
             * Save the input stream from the client as a JPEG file
             */
            return null;
        }
        catch (IOException e) {
//            Log.e(WiFiDirectActivity.TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Song result){
        // TODO - add song to playlist
    }
}
