package com.mycompany.plarty;


import android.net.wifi.p2p.WifiP2pDevice;

public class Peer {
    private String peerName;
    private String peerAddress;

    public Peer(){
        this.peerName = null;
        this.peerAddress = null;
    }

    public Peer(WifiP2pDevice peer){
        this.peerName = peer.deviceName;
        this.peerAddress = peer.deviceAddress;
    }

    public String getName(){
        return this.peerName;
    }

    public String getAddress(){
        return this.peerAddress;
    }
}
