package com.mycompany.plarty;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Antonio San Pedro on 4/30/2016.
 */
public class ClientAsync extends AsyncTask<String, Socket, Socket> {

    private Exception exception;

    protected Socket doInBackground(String... type) {
        System.out.println("Connecting...");
        try {
            Socket socket = new Socket("10.146.57.72", 7777);
            return socket;
        } catch(Exception e){ }
        System.out.println("Connection successful");
        return null;
    }

    protected void onPostExecute(Socket socket) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
