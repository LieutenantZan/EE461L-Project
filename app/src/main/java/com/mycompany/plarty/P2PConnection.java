package com.mycompany.plarty;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class P2PConnection {

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    public P2PConnection(Activity activity){

        mManager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(activity, activity.getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, activity);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    public void resumeRegister(Activity activity){
        activity.registerReceiver(mReceiver, mIntentFilter);
    }

    public void pauseUnregister(Activity activity){
        activity.unregisterReceiver(mReceiver);
    }

    public void detectPeers(Activity activity){
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("P2P", "Discovered Peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.d("P2P", "Can't detect peers");
            }
        });
    }

    public void peerConnect(WifiP2pDevice peer){

        //obtain a peer from the WifiP2pDeviceList
        WifiP2pDevice device = peer;
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                //success logic
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
            }
        });
    }

    public void sendSong(Activity activity, Song song){
        // TODO - actually do it
        Context context = activity.getApplicationContext();
        String host;
        int port;
        int len;
        Socket socket = new Socket();
        byte buf[]  = new byte[1024];

        try {
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            socket.bind(null);
//            socket.connect((new InetSocketAddress(host, port)), 500);

            /**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            inputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"));
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            //catch logic
        } catch (IOException e) {
            //catch logic
        }

        /**
         * Clean up any open sockets when done
         * transferring or if an exception occurred.
         */
        finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
                    }
                }
            }
        }
    }

    public void renameDevice(String name){
        try {
            Method m = mManager.getClass().getMethod("setDeviceName", mChannel.getClass(), String.class, WifiP2pManager.ActionListener.class);
            m.invoke(mManager, mChannel, name, new WifiP2pManager.ActionListener(){
                @Override
                public void onSuccess(){
                    Log.d("P2P", "Device Renamed!");
                }

                @Override
                public void onFailure(int reason){
                    Log.d("P2P", "Device rename failed");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
