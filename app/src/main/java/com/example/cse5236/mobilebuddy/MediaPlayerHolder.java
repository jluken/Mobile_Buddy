package com.example.cse5236.mobilebuddy;

import android.media.MediaPlayer;
import java.io.Serializable;

/**
 * Created by Amir on 3/31/2018.
 */

public class MediaPlayerHolder implements Serializable {
    public MediaPlayer mediaPlayer;

    public MediaPlayerHolder(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }
}