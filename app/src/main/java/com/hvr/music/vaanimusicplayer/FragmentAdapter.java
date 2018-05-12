package com.hvr.music.vaanimusicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    FragmentAdapter(FragmentManager fm) {
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
