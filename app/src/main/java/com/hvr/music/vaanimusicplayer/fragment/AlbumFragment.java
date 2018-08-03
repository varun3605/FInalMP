package com.hvr.music.vaanimusicplayer.fragment;

import android.content.ContentResolver;
import android.content.Intent;
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

import com.hvr.music.vaanimusicplayer.MiddleListActivity;
import com.hvr.music.vaanimusicplayer.handler.AlbumListHandler;
import com.hvr.music.vaanimusicplayer.R;
import com.hvr.music.vaanimusicplayer.model.Album;

import java.util.List;

public class AlbumFragment extends Fragment {

    private ContentResolver mContentResolver;
    private RecyclerView mRecyclerView;
    private AlbumFragment.AlbumAdapter mAlbumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);

        mContentResolver = getActivity().getContentResolver();
        mRecyclerView = view.findViewById(R.id.AlbumView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(true);

        updateUI();
        return view;
    }

    private void updateUI()
    {
        AlbumListHandler albumListHandler = AlbumListHandler.get(getActivity(),mContentResolver);
        List<Album> albums = albumListHandler.getAlbums();

        mAlbumAdapter = new AlbumFragment.AlbumAdapter(albums);
        mRecyclerView.setAdapter(mAlbumAdapter);
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumFragment.AlbumHolder>
    {

        private final List<Album> mAlbums;

        private AlbumAdapter(List<Album> songs) {
            mAlbums = songs;
        }

        @NonNull
        @Override
        public AlbumFragment.AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AlbumFragment.AlbumHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumFragment.AlbumHolder holder, int position) {
            Album song = mAlbums.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }
    }

    private class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final TextView mAlbumNameTextView;
        private Album mAlbum;

        private AlbumHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_album,parent,false));
            itemView.setOnClickListener(this);

            mAlbumNameTextView = itemView.findViewById(R.id.album_name);
        }

        void bind(Album song)
        {
            mAlbum = song;
            mAlbumNameTextView.setText(mAlbum.getAlbumName());
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = MiddleListActivity.newIntent(getActivity(), mAlbum);
            startActivity(intent);
        }
    }
}
