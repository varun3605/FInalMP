package com.hvr.music.vaanimusicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hvr.music.vaanimusicplayer.handler.AlbumListHandler;
import com.hvr.music.vaanimusicplayer.model.Album;
import com.hvr.music.vaanimusicplayer.model.Song;

import java.util.List;

public class MiddleListActivity extends AppCompatActivity
{
    private static final String EXTRA_ALBUM_ID = "com.hvr.music.vaanimusicplayer.album_id";
    private static final String EXTRA_ALBUM_NAME = "com.hvr.music.vaanimusicplayer.album_name";
    private ContentResolver mContentResolver;
    private RecyclerView mRecyclerView;
    private MiddleListAdapter mMiddleListAdapter;
    String mAlbumId,mAlbumName;
    ActionBar mActionBar;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_list);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAlbumId = getIntent().getStringExtra(EXTRA_ALBUM_ID);
        mAlbumName = getIntent().getStringExtra(EXTRA_ALBUM_NAME);
        Log.i("App",mAlbumId + "");

        mActionBar = getSupportActionBar();
        mActionBar.setTitle(mAlbumName);

        mContentResolver = getContentResolver();
        mRecyclerView = findViewById(R.id.middle_click_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MiddleListActivity.this));
        mRecyclerView.setNestedScrollingEnabled(true);

        updateUI();
    }

    public static Intent newIntent(Context context, Album album)
    {
        Intent intent = new Intent(context, MiddleListActivity.class);
        intent.putExtra(EXTRA_ALBUM_ID, album.getId());
        intent.putExtra(EXTRA_ALBUM_NAME,album.getAlbumName());
        return intent;
    }

    private void updateUI()
    {
        AlbumListHandler middleListHandler = new AlbumListHandler();
        List<Song> mSongs = middleListHandler.AlbumSongRetriever(mContentResolver,mAlbumId);

        mMiddleListAdapter = new MiddleListAdapter(mSongs);
        mRecyclerView.setAdapter(mMiddleListAdapter);
    }

    private class MiddleListAdapter extends RecyclerView.Adapter<MiddleListHolder>
    {

        private final List<Song> mSonginAlbum;

        private MiddleListAdapter(List<Song> songs) {
            mSonginAlbum = songs;
        }

        @NonNull
        @Override
        public MiddleListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MiddleListActivity.this);
            return new MiddleListHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MiddleListHolder holder, int position) {
            Song song = mSonginAlbum.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            return mSonginAlbum.size();
        }
    }

    private class MiddleListHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final TextView mSongNameTextView;
        private final TextView mArtistNameTextView;
        private final TextView mDurationTextView;
        private Song mSong;

        private MiddleListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_track,parent,false));
            itemView.setOnClickListener(this);

            mSongNameTextView = itemView.findViewById(R.id.song_name);
            mArtistNameTextView = itemView.findViewById(R.id.artist_name);
            mDurationTextView = itemView.findViewById(R.id.song_duration);
        }

        void bind(Song song)
        {
            mSong = song;
            mSongNameTextView.setText(mSong.getSongName());
            mArtistNameTextView.setText(mSong.getArtistName());
            mDurationTextView.setText(mSong.getDuration());
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = NowPlayingActivity.newIntent(MiddleListActivity.this, mSong);
            startActivity(intent);
        }
    }
}
