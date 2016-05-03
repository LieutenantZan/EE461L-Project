package com.mycompany.plarty;

import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Antonio San Pedro on 4/30/2016.
 */
public class Client {

    public static Socket getSocket() {
        return socket;
    }

    String ipaddress = "10.146.57.72";
    static Socket socket;
    static DataOutputStream out;
    static DataInputStream in;

    public Client() throws Exception{
        ClientAsync temp = new ClientAsync();
        temp.execute();
        try{
            socket = temp.get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void outputToServer(Song song) throws Exception{
        out = new DataOutputStream(socket.getOutputStream());
        Output output = new Output(out,socket,song);
        Thread thread = new Thread(output);
        thread.start();
    }

    public void inputFromServer() throws Exception{
        in = new DataInputStream(socket.getInputStream());
        Input input = new Input(in,socket);
        Thread thread2 = new Thread(input);
        thread2.start();
    }
}

class Input implements Runnable{

    DataInputStream in;
    Socket socket;

    public Input(DataInputStream in, Socket socket){
        this.in = in;
    }

    public void run(){
        while(true) {
            try {
                String message = in.readUTF();
                System.out.println(message);
                Song song = new Song(message);
                MainActivity.playlist.queueSong(song);
                //Song song = new Song(message);
                //MainActivity.playlist.queueSong(song);
            } catch (IOException e) {
            } //catch(JSONException e){ }
        }
    }
}

class Output implements Runnable{

    DataOutputStream out;
    Socket socket;
    Song song;

    public Output(DataOutputStream out, Socket socket, Song song){
        this.out = out;
        this.song = song;
    }

    public void run(){
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

        try{
            out.writeUTF(json.toString());
            out.flush();
            System.out.println("Sent! " + json.toString());
        } catch (IOException e) {}
    }
}
