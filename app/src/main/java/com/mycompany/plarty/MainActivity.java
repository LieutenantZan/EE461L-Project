package com.mycompany.plarty;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "9409b36d6d4143188ac37ffc294ed91a";
    private static final String REDIRECT_URI = "plarty-login://callback";
    public static final String API_URL = "https://api.spotify.com/v1/search?q=";
    private static final int SPOTIFY_LOGIN_REQUEST_CODE = 1337;
    private static final int HOST_ROOM_REQUEST_CODE = 200;
    public static String spotifyTrackID; //Keeps track of the saved song
    public static String txt;
    public static Player mPlayer;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mycompany.plarty.R.layout.activity_main);
        startP2P();
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
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);
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
        super.onDestroy();
    }

    public void onSearch(View view) {
        Intent searchIntent = new Intent(this, Search.class);
        startActivity(searchIntent);
    }

    public void onCreateRoom(View view) {
        //TODO - Start broadcasting to devices
            //Check out http://developer.android.com/training/connect-devices-wirelessly/index.html
            //http://developer.android.com/guide/topics/connectivity/wifip2p.html
            //Maybe move it up to after the intent is returned and the room is actually created so the name can be the party name
            //Maybe make it name:code
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, SPOTIFY_LOGIN_REQUEST_CODE, request);

        Intent createRoomIntent = new Intent(this, CreateRoom.class);
        final int result = 100;
        startActivityForResult(createRoomIntent, HOST_ROOM_REQUEST_CODE);
    }

    public void onLeaveRoom(View view) {
        setContentView(com.mycompany.plarty.R.layout.activity_main);
        //TODO - Close room and all that jazz goes here too
    }

    public void onPlaySong(View view){
        EditText et = (EditText) findViewById(com.mycompany.plarty.R.id.editText);
        txt = et.getText().toString();
        txt = txt.replaceAll(" ", "%20");
        String url_select = API_URL + txt + "&type=track";
        new JSONTask().execute(url_select);
    }

    public void onJoinRoom(View view) {
        //TODO - Search for devices and join
//        connection.detectPeers(this.getParent());
    }

    private void startP2P(){
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        connection.resumeRegister(this.getParent());
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
//        connection.pauseUnregister(this.getParent());
    }
}