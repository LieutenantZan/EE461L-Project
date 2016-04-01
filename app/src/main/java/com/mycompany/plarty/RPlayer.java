package com.mycompany.plarty;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio San Pedro on 3/31/2016.
 */
public class RPlayer implements
        PlayerNotificationCallback, ConnectionStateCallback {

    List<TrackModel> chosenTracks = new ArrayList<TrackModel>();
    List<TrackModel> oldTracks = new ArrayList<TrackModel>();
    public static Player mPlayer;
    boolean isPlaying;

    RPlayer(Player player){
        mPlayer = player;
        mPlayer.addConnectionStateCallback(RPlayer.this);
        mPlayer.addPlayerNotificationCallback(RPlayer.this);
    }

    public void queueTrack(TrackModel track){
        if (!chosenTracks.contains(track)) {
            mPlayer.getPlayerState(new PlayerStateCallback() {
                @Override
                public void onPlayerState(PlayerState playerState) {
                    isPlaying = playerState.playing;
                    System.out.println("Player State is " + isPlaying);
                }
            });
            if (chosenTracks.isEmpty() && !isPlaying) {
                System.out.println("No Track Playing, Play This Track");
                oldTracks.add(track);
                mPlayer.play(track.getItem().getUri());
                System.out.println("Track Set to Play");
            } else {
                chosenTracks.add(track);
            }
        }
    }

    public void playNext(){
        if(!chosenTracks.isEmpty()) {
            mPlayer.play(chosenTracks.get(0).getItem().getUri());
            oldTracks.add(chosenTracks.get(0));
            chosenTracks.remove(0);
            System.out.println("Song Playing");
        }
    }

    public void playPrev(){
        if(!oldTracks.isEmpty()) {
            chosenTracks.add(0, oldTracks.get(oldTracks.size() - 1));
            mPlayer.play(oldTracks.get(oldTracks.size() - 2).getItem().getUri());
            oldTracks.remove(oldTracks.size()-1);
            System.out.println("Song Playing");
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

    protected void onDestroy() {
        System.out.println("player destroyed");
        Spotify.destroyPlayer(this);
    }
}
