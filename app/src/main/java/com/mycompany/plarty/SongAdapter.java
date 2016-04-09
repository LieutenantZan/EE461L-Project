package com.mycompany.plarty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SongAdapter extends ArrayAdapter<Song> {

    private static class ViewHolder {
        TextView song;
        TextView artist;
        TextView album;
        ImageView albumArt;
    }

    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Song song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_view, parent, false);
            viewHolder.song = (TextView) convertView.findViewById(R.id.songName);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artistName);
            viewHolder.album = (TextView) convertView.findViewById(R.id.albumName);
            viewHolder.albumArt = (ImageView) convertView.findViewById(R.id.albumArt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
//        TextView songName = (TextView) convertView.findViewById(R.id.songName);
//        TextView artist = (TextView) convertView.findViewById(R.id.artistName);
//        TextView album = (TextView) convertView.findViewById(R.id.albumName);
//        ImageView albumArt = (ImageView) convertView.findViewById(R.id.albumArt);
        // Populate the data into the template view using the data object
        viewHolder.song.setText(song.getSong());
        viewHolder.artist.setText(song.getArtist());
        viewHolder.album.setText(song.getAlbum());
        viewHolder.albumArt.setImageBitmap(song.getAlbumArt());

        // Return the completed view to render on screen
        return convertView;
    }
}