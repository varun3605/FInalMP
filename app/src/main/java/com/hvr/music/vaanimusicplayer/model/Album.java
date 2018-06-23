package com.hvr.music.vaanimusicplayer.model;

public class Album
{
    private String mId;
    private String mAlbumName;
    private String mNumberofSongs;
    private int mAlbumDuration;

    public Album() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getNumberofSongs() {
        return mNumberofSongs;
    }

    public void setNumberofSongs(String numberofSongs) {
        mNumberofSongs = numberofSongs;
    }

    public int getAlbumDuration() {
        return mAlbumDuration;
    }

    public void setAlbumDuration(int albumDuration) {
        mAlbumDuration = albumDuration;
    }
}
