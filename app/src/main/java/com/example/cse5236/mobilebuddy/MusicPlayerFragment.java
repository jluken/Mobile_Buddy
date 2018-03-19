package com.example.cse5236.mobilebuddy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.database.Cursor;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amir on 3/15/2018.
 */

public class MusicPlayerFragment extends Fragment{

    private MediaPlayer mediaPlayer;
    private Uri musicUri;
    private ArrayList<SongInfoObj> songList;
    private SongListAdapter slAdapter;
    private int currentPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_player, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        songList = new ArrayList<SongInfoObj>();
        getSongList();

        slAdapter = new SongListAdapter(getActivity(), songList);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        final ListView songView = getView().findViewById(R.id.song_list);
        songView.setAdapter(slAdapter);

        songView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPos = position;
                playSong();
            }
        });

        final Button playButton = getView().findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });

        final Button pauseButton = getView().findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });

        final Button skipButton = getView().findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentPos == songList.size() - 1)
                    currentPos = 0;
                else
                    currentPos += 1;
                playSong();
            }
        });
    }

    public void getSongList(){
        ContentResolver musicContent = getActivity().getContentResolver();
        musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = musicContent.query(musicUri, null, null, null, null);

        if(cursor!=null && cursor.moveToFirst()){
            //get columns
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long ID = cursor.getLong(idColumn);
                String title = cursor.getString(titleColumn);
                String artist = cursor.getString(artistColumn);
                songList.add(new SongInfoObj(title, ID, artist));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
    }

    public void playSong(){
        mediaPlayer.reset();
        musicUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songList.get(currentPos).getID());
        try {
            mediaPlayer.setDataSource(getActivity(), musicUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
}
