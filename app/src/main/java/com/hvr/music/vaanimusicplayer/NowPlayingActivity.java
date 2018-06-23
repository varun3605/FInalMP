package com.hvr.music.vaanimusicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hvr.music.vaanimusicplayer.model.Song;

public class NowPlayingActivity extends AppCompatActivity
{
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private static final String EXTRA_SONG_OBJECT = "com.hvr.music.vaanimusicplayer.song_object";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "song_position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private String mSongId;
    private boolean mSongAutoPlay;
    private long mSongPlaybackPosition;
    private int mStartWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        if(savedInstanceState != null)
        {
            mSongAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            mStartWindow = savedInstanceState.getInt(KEY_WINDOW);
            mSongPlaybackPosition = savedInstanceState.getLong(KEY_POSITION);
        }

        mSongId = getIntent().getStringExtra(EXTRA_SONG_OBJECT);

        mPlayerView = findViewById(R.id.now_playing_player);
        initializePlayer();
    }

    private void initializePlayer()
    {
        mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mPlayer);

        mPlayer.setPlayWhenReady(mSongAutoPlay);
        mPlayer.seekTo(mStartWindow, mSongPlaybackPosition);

        Uri SongUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mSongId);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(NowPlayingActivity.this, Util.getUserAgent(NowPlayingActivity.this, getResources().getString(R.string.app_name)))).createMediaSource(SongUri);
        mPlayer.prepare(mediaSource, true, false);
    }

    private void releasePlayer()
    {
        if(mPlayer != null)
        {
            updateStartPosition();
            mPlayer.release();
            mPlayer = null;
        }
    }


    private void updateStartPosition()
    {
        if (mPlayer != null)
        {
            mSongAutoPlay = mPlayer.getPlayWhenReady();
            mStartWindow = mPlayer.getCurrentWindowIndex();
            mSongPlaybackPosition = Math.max(0, mPlayer.getContentPosition());
        }
    }

    private void clearStartPosition()
    {
        mSongAutoPlay = true;
        mStartWindow = C.INDEX_UNSET;
        mSongPlaybackPosition = C.TIME_UNSET;
    }

    public static Intent newIntent(Context context, Song song)
    {
        Intent intent = new Intent(context, NowPlayingActivity.class);
        intent.putExtra(EXTRA_SONG_OBJECT, song.getId());
        return intent;
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        releasePlayer();
        clearStartPosition();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(Util.SDK_INT > 23)
            initializePlayer();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        hideSystemUi();
        if((Util.SDK_INT <= 23 || mPlayer == null))
            initializePlayer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(Util.SDK_INT > 23)
            releasePlayer();
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi()
    {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        updateStartPosition();
        outState.putBoolean(KEY_AUTO_PLAY, mSongAutoPlay);
        outState.putInt(KEY_WINDOW, mStartWindow);
        outState.putLong(KEY_POSITION, mSongPlaybackPosition);
        super.onSaveInstanceState(outState);

    }
}
