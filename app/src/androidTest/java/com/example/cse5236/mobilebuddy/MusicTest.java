package com.example.cse5236.mobilebuddy;


import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class MusicTest {
    @Rule
    public ActivityTestRule<Music> musicActivityTestRule = new ActivityTestRule<>(Music.class);


    @Test
    public void testMusicOnCreateInitializesObjects()
    {
        musicActivityTestRule.getActivity().onCreate(Bundle.EMPTY);


        Assert.assertTrue(musicActivityTestRule.getActivity().mediaPlayer != null);
        Assert.assertTrue(musicActivityTestRule.getActivity().bundle.containsKey("player"));

    }
}
