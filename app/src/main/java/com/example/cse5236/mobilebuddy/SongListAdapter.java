package com.example.cse5236.mobilebuddy;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import org.w3c.dom.Text;

/**
 * Created by Amir on 3/16/2018.
 */

class SongListAdapter extends ArrayAdapter<SongInfoObj> {

    private final Context context;
    private final SongInfoObj songList[];

    public SongListAdapter(Context context, ArrayList<SongInfoObj> songList){
        super(context, -1, songList);
        this.context = context;
        this.songList = songList.toArray(new SongInfoObj[songList.size()]);
    }

    @Override
    public int getCount() {
        return songList.length;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.song_list_item, container, false);

            holder = new ViewHolder();

            holder.songTitle = view.findViewById(R.id.firstLine);
            holder.artistName = view.findViewById(R.id.secondLine);
            holder.albumCover = view.findViewById(R.id.albumCover);

            view.setTag(holder);

            // set album cover?
        } else
            holder = (ViewHolder) view.getTag();

        holder.songTitle.setText(songList[position].getTitle());
        holder.artistName.setText(songList[position].getArtist());

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder{
        public TextView songTitle, artistName;
        public ImageView albumCover;
    }
}