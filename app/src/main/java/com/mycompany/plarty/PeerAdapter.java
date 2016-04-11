package com.mycompany.plarty;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PeerAdapter extends ArrayAdapter<Peer> {

    private static class ViewHolder {
        TextView name;
    }

    public PeerAdapter(Context context, ArrayList<Peer> peer) {
        super(context, 0, peer);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Peer peer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_view, parent, false);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.peerName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        // Populate the data into the template view using the data object
        viewHolder.name.setText(peer.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}