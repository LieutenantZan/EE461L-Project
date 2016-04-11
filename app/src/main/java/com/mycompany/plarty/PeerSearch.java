package com.mycompany.plarty;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PeerSearch extends Activity {

    private static ArrayList<Peer> peerList;
    private PeerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_search);

        ArrayList<Peer> arrayOfPeers = new ArrayList<Peer>();
        adapter = new PeerAdapter(this, arrayOfPeers);
        ListView listView = (ListView) findViewById(R.id.lvPeers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO - Connect to the peer
            }
        });
    }

    public void onRefresh(View view) {
        adapter.clear();
        MainActivity.test.detectPeers(this);
        if(peerList == null) {
            //TODO - Display no peers
        } else {
            for (int i = 0; i < peerList.size(); i += 1) {
                Peer newPeer = peerList.get(i);
                adapter.add(newPeer);
            }
        }
    }

    public static void setPeerList(WifiP2pDeviceList list) {
        ArrayList<WifiP2pDevice> tmp = new ArrayList<WifiP2pDevice>(list.getDeviceList());
        for (int i = 0; i < tmp.size(); i += 1){
            peerList.add(new Peer(tmp.get(i)));
        }
    }
}
