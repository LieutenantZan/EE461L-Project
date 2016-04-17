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
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class P2PConnection {

    private static final int SERVER_PORT = 6000;
    private static final String TAG = "P2P";
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    final HashMap<String, String> buddies = new HashMap<String, String>();

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

    public void startRegistration() {
        //  Create a string map containing information about your service.
        Map record = new HashMap();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "John Doe" + (int) (Math.random() * 1000));
        record.put("available", "visible");

        // Service information.  Pass it an instance name, service type
        // _protocol._transportlayer , and the map containing
        // information other devices will want once they connect to this one.
        WifiP2pDnsSdServiceInfo serviceInfo =
                WifiP2pDnsSdServiceInfo.newInstance("_test", "_http._tcp", record);

        // Add the local service, sending the service info, network channel,
        // and listener that will be used to indicate success or failure of
        // the request.
        mManager.addLocalService(mChannel, serviceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
                Log.d(TAG, "Local Service Added");
            }

            @Override
            public void onFailure(int arg0) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
            }
        });
    }

    public void discoverService() {
        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {
        /* Callback includes:
         * fullDomain: full domain name: e.g "printer._ipp._tcp.local."
         * record: TXT record dta as a map of key/value pairs.
         * device: The device running the advertised service.
         */
            @Override
            public void onDnsSdTxtRecordAvailable(
                    String fullDomain, Map record, WifiP2pDevice device) {
                Log.d(TAG, "DnsSdTxtRecord available -" + record.toString());
                buddies.put(device.deviceAddress, record.get("buddyname").toString());
            }
        };

        WifiP2pManager.DnsSdServiceResponseListener servListener = new WifiP2pManager.DnsSdServiceResponseListener() {
            @Override
            public void onDnsSdServiceAvailable(String instanceName, String registrationType,
                                                WifiP2pDevice resourceType) {

                // Update the device name with the human-friendly version from
                // the DnsTxtRecord, assuming one arrived.
                resourceType.deviceName = buddies
                        .containsKey(resourceType.deviceAddress) ? buddies
                        .get(resourceType.deviceAddress) : resourceType.deviceName;

                // Add to the custom adapter defined specifically for showing
                // wifi devices.
//                WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
//                        .findFragmentById(R.id.frag_peerlist);
//                PeerAdapter adapter = ((PeerAdapter) fragment.getListAdapter());
//
//                adapter.add(resourceType);
//                adapter.notifyDataSetChanged();
                Log.d(TAG, "onBonjourServiceAvailable " + instanceName);
            }
        };

        mManager.setDnsSdResponseListeners(mChannel, servListener, txtListener);

        WifiP2pDnsSdServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        mManager.addServiceRequest(mChannel,
                serviceRequest,
                new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        // Success!
                        Log.d(TAG, "Added Service Request");
                    }

                    @Override
                    public void onFailure(int code) {
                        // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
                    }
                });
        mManager.discoverServices(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Discovered Peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "Can't detect peers");
            }
        });
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
                Log.d(TAG, "Detected Peers");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "No Peers Detected");
            }
        });
    }


//    public void peerConnect(WifiP2pDevice peer){
//
//        //obtain a peer from the WifiP2pDeviceList
//        WifiP2pDevice device = peer;
//        WifiP2pConfig config = new WifiP2pConfig();
//        config.deviceAddress = device.deviceAddress;
//        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
//
//            @Override
//            public void onSuccess() {
//                //success logic
//            }
//
//            @Override
//            public void onFailure(int reason) {
//                //failure logic
//            }
//        });
//    }
//
//    public void sendSong(Activity activity, Song song){
//        // TODO - actually do it
//        Context context = activity.getApplicationContext();
//        String host;
//        int port;
//        int len;
//        Socket socket = new Socket();
//        byte buf[]  = new byte[1024];
//
//        try {
//            /**
//             * Create a client socket with the host,
//             * port, and timeout information.
//             */
//            socket.bind(null);
////            socket.connect((new InetSocketAddress(host, port)), 500);
//
//            /**
//             * Create a byte stream from a JPEG file and pipe it to the output stream
//             * of the socket. This data will be retrieved by the server device.
//             */
//            OutputStream outputStream = socket.getOutputStream();
//            ContentResolver cr = context.getContentResolver();
//            InputStream inputStream = null;
//            inputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"));
//            while ((len = inputStream.read(buf)) != -1) {
//                outputStream.write(buf, 0, len);
//            }
//            outputStream.close();
//            inputStream.close();
//        } catch (FileNotFoundException e) {
//            //catch logic
//        } catch (IOException e) {
//            //catch logic
//        }
//
//        /**
//         * Clean up any open sockets when done
//         * transferring or if an exception occurred.
//         */
//        finally {
//            if (socket != null) {
//                if (socket.isConnected()) {
//                    try {
//                        socket.close();
//                    } catch (IOException e) {
//                        //catch logic
//                    }
//                }
//            }
//        }
//    }
//
//    public void renameDevice(String name){
//        try {
//            Method m = mManager.getClass().getMethod("setDeviceName", mChannel.getClass(), String.class, WifiP2pManager.ActionListener.class);
//            m.invoke(mManager, mChannel, name, new WifiP2pManager.ActionListener(){
//                @Override
//                public void onSuccess(){
//                    Log.d("P2P", "Device Renamed!");
//                }
//
//                @Override
//                public void onFailure(int reason){
//                    Log.d("P2P", "Device rename failed");
//                }
//            });
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
