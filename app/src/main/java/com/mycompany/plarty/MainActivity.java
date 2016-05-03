package com.mycompany.plarty;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback, Observer {

    private static final String CLIENT_ID = "9409b36d6d4143188ac37ffc294ed91a";
    private static final String REDIRECT_URI = "plarty-login://callback";
    public static final String API_URL = "https://api.spotify.com/v1/search?q=";
    private static final int SPOTIFY_LOGIN_REQUEST_CODE = 1337;
    private static final int HOST_ROOM_REQUEST_CODE = 200;
    public static String spotifyTrackID; //Keeps track of the saved song
    public static String txt;
    public static Player mPlayer;
    public static Playlist playlist;
    public static Client client;
    public static boolean host = false;
//    public static String logoPath="app/src/main/res/layout/img/logo.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Drawable logo = Drawable.createFromPath(logoPath);
        setContentView(com.mycompany.plarty.R.layout.activity_main);
        playlist = new Playlist();
        playlist.addObserver(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == SPOTIFY_LOGIN_REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Spotify.getPlayer(new Config(this, response.getAccessToken(), CLIENT_ID), this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        mPlayer = player;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(new PlayerNotificationCallback() {
                            @Override
                            public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
                                if (eventType == EventType.END_OF_CONTEXT){
                                    playNext(playlist.getNext());
                                }
                            }

                            @Override
                            public void onPlaybackError(ErrorType errorType, String s) {

                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }

        if (requestCode == HOST_ROOM_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                setContentView(R.layout.room_view);
                TextView roomNameMessage = (TextView) findViewById(R.id.roomNameDisplay);
                String roomName = intent.getStringExtra("RoomName");
                TextView roomCodeMessage = (TextView) findViewById(R.id.roomCodeDisplay);
                String roomCode = intent.getStringExtra("RoomCode");
                roomNameMessage.setText(roomName);
                roomCodeMessage.setText(roomCode);
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        playlist.clearPlaylist();
        super.onDestroy();
    }

    public void onCreateRoom(View view) {
        //TODO - Start broadcasting to devices
            //Check out http://developer.android.com/training/connect-devices-wirelessly/index.html
            //Maybe move it up to after the intent is returned and the room is actuially created so the name can be the party name
            //Maybe make it name:code
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, SPOTIFY_LOGIN_REQUEST_CODE, request);

        try {
            client = new Client();
        } catch(Exception e){ }
        host = false;

        Intent createRoomIntent = new Intent(this, CreateRoom.class);
        final int result = 100;
        startActivityForResult(createRoomIntent, HOST_ROOM_REQUEST_CODE);
    }

    public void onLeaveRoom(View view) {
        setContentView(com.mycompany.plarty.R.layout.activity_main);
        //TODO - Close room and all that jazz goes here too
        onDestroy();
    }

//    public void onPlaySong(View view){
//        EditText et = (EditText) findViewById(com.mycompany.plarty.R.id.editText);
//        txt = et.getText().toString();
//        txt = txt.replaceAll(" ", "%20");
//        String url_select = API_URL + txt + "&type=track";
//        new JSONTask().execute(url_select);
//    }
    public void queueSong(Song song){
        if(host){
            playlist.queueSong(song);
        } else{
            try {
                client.outputToServer(song);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onJoinRoom(View view) {
        //TODO - Search for devices and join
        try {
            client = new Client();
        } catch(Exception e){ }
        host = false;
    }

    public void onSearch(View view) {
        Intent searchIntent = new Intent(this, Search.class);
        startActivity(searchIntent);
    }

    private void playNext(Song next){
        if(next == null){
            Toast.makeText(MainActivity.this, "Playlist Empty", Toast.LENGTH_SHORT).show();
        } else {
            mPlayer.play(next.getURL());
            updateNowPlaying(next);
        }
    }

    public void update(Observable o, Object data){
        mPlayer.getPlayerState(new PlayerStateCallback() {
            @Override
            public void onPlayerState(PlayerState playerState) {
                if (playerState.playing == false){
                    playNext(playlist.getNext());
                }
            }
        });
    }

    public void onViewPlaylist(View view){
        Intent playlistIntent = new Intent(this, PlaylistView.class);
        startActivity(playlistIntent);
    }

    private void updateNowPlaying(Song nowPlaying){
        ImageView albumArt = (ImageView) findViewById(R.id.albumArtNow);
        TextView song = (TextView) findViewById(R.id.songNameNow);
        TextView artist = (TextView) findViewById(R.id.artistNameNow);
        TextView album = (TextView) findViewById(R.id.albumNameNow);

        albumArt.setImageBitmap(nowPlaying.getAlbumArt());
        song.setText(nowPlaying.getSong());
        artist.setText(nowPlaying.getArtist());
        album.setText(nowPlaying.getAlbum());
    }
}