package com.hvr.music.vaanimusicplayer.handler;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.hvr.music.vaanimusicplayer.model.Album;
import com.hvr.music.vaanimusicplayer.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumListHandler
{
    private static AlbumListHandler sAlbumListHandler;
    private List<Album> mAlbums;
    private List<Song> mSonginAlbum;

    public AlbumListHandler() {
    }

    public static AlbumListHandler get(Context context, ContentResolver contentResolver)
    {
        if(sAlbumListHandler==null)
        {
            sAlbumListHandler = new AlbumListHandler(contentResolver);
        }
        return sAlbumListHandler;
    }

    private AlbumListHandler(ContentResolver contentResolver)
    {
        String sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER + " ASC";

        mAlbums = new ArrayList<>();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,null,null,sortOrder);
        for(int i=0;i<cursor.getCount();i++)
        {
            mAlbums.clear();
            if (cursor.moveToFirst()) {
                do {
                    Album album = new Album();
                    album.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
                    album.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
                    album.setNumberofSongs(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));

                    mAlbums.add(album);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public List<Song> AlbumSongRetriever(ContentResolver contentResolver, String mAlbumId)
    {
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER + " ASC";
        String where = MediaStore.Audio.Media.ALBUM_ID + "=?";
        String[] albumName = {mAlbumId};
        mSonginAlbum = new ArrayList<>();

        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,where,albumName,sortOrder);
        for(int i=0;i<cursor.getCount();i++)
        {
            mSonginAlbum.clear();
            if (cursor.moveToFirst()) {
                do {
                    Song song = new Song();
                    song.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    song.setSongName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    song.setAlbumName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    song.setArtistName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    song.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                    //Log.i("App", song.getAlbumName() + " " + song.getSongName() + " " + song.getArtistName() + " " + song.getDuration());

                    mSonginAlbum.add(song);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return mSonginAlbum;
    }

    public List<Album> getAlbums()
    {
        return mAlbums;
    }
}
