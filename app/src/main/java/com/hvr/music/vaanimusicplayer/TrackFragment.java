package com.hvr.music.vaanimusicplayer;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TrackFragment extends Fragment{
    private ContentResolver mContentResolver;
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_track,container,false);

        mContentResolver = getActivity().getContentResolver();
        mRecyclerView = view.findViewById(R.id.TrackView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI()
    {
        SongListHandler songListHandler = SongListHandler.get(getActivity(),mContentResolver);
        List<Song> songs = songListHandler.getSongs();

        mSongAdapter = new SongAdapter(songs);
        mRecyclerView.setAdapter(mSongAdapter);
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder>
    {

        private List<Song> mSongs;

        private SongAdapter(List<Song> songs) {
            mSongs = songs;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SongHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }
    }

    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView mSongNameTextView;
        private TextView mArtistNameTextView;
        private TextView mDurationTextView;
        private Song mSong;

        private SongHolder(LayoutInflater inflater, ViewGroup parent) {
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

        }
    }
}
