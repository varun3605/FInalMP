package com.hvr.music.vaanimusicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hvr.music.vaanimusicplayer.fragment.AlbumFragment;
import com.hvr.music.vaanimusicplayer.fragment.ArtistFragment;
import com.hvr.music.vaanimusicplayer.fragment.FolderFragment;
import com.hvr.music.vaanimusicplayer.fragment.GenreFragment;
import com.hvr.music.vaanimusicplayer.fragment.PlaylistFragment;
import com.hvr.music.vaanimusicplayer.fragment.TrackFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: return new TrackFragment();
            case 1: return new AlbumFragment();
            case 2: return new ArtistFragment();
            case 3: return new GenreFragment();
            case 4: return new PlaylistFragment();
            case 5: return new FolderFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }


}
