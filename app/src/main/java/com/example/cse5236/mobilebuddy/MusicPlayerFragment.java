package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.database.Cursor;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.widget.ImageView;
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
    private boolean started;
    private ImageView playPauseButton, skipButton;
    private ListView songView;

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
        mediaPlayer = Music.mediaPlayer;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        started = false;

        songView = getView().findViewById(R.id.song_list);
        songView.setAdapter(slAdapter);

        songView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPos = position;
                playSong();
                playPauseButton.setImageResource(R.drawable.pausebutton);
            }
        });

        playPauseButton = getView().findViewById(R.id.playButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()){
                    if (started) {
                        mediaPlayer.start();
                        playPauseButton.setImageResource(R.drawable.pausebutton);
                    }
                }else {
                    mediaPlayer.pause();
                    playPauseButton.setImageResource(R.drawable.playbutton);
                }
            }
        });

        skipButton = getView().findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (started) {
                    if (currentPos == songList.size() - 1)
                        currentPos = 0;
                    else
                        currentPos += 1;
                    playSong();
                    playPauseButton.setImageResource(R.drawable.pausebutton);
                }
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
        started = true;
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
        Activity active = getActivity();
        int currentBoredomStat = HomeScreenActivity.getStat(active, "boredom" );
        HomeScreenActivity.setStat(active, "boredom", currentBoredomStat - 5);
    }
}
